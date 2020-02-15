package com.microservice.peopleservice.repository

import com.microservice.peopleservice.poko.UserStatus
import com.microservice.peopleservice.poko.type.UserStatusType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import redis.clients.jedis.JedisPool

@Repository
class UserStatusRepository {
    @Autowired
    private lateinit var jedisPool: JedisPool
    private val userPrefix = "User"
    private val statusField = "status"
    private val statusValue = "value"
    private val statusUpdateTime = "updateTime"

    fun update(statusType: UserStatusType, username: String, timestamp: Long) {
        jedisPool.resource.use { jedis ->
            with(jedis) {
                hset("$userPrefix:$username", "$statusField:$statusValue", statusType.toString())
                hset("$userPrefix:$username", "$statusField:$statusUpdateTime", timestamp.toString())
            }
        }
    }

    fun get(username: String): UserStatus {
        var userInfo = mutableMapOf<String, String>()
        jedisPool.resource.use {
            userInfo = it.hgetAll("$userPrefix:$username")
        }
        if (userInfo.isEmpty()) {
            return UserStatus()
        }
        val statusValue = UserStatusType.valueOf(userInfo["$statusField:$statusValue"]!!)
        val updateTime = userInfo["$statusField:$statusUpdateTime"]!!.toLong()

        return UserStatus(statusValue, username, updateTime)
    }
}
