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
    private val uidPrefix = "uid"
    private val statusField = "status"
    private val statusValue = "value"
    private val statusUpdateTime = "updateTime"
    private val statusUser = "username"

    fun update(statusType: UserStatusType, uid: String, timestamp: Long, username: String) {
        jedisPool.resource.use { jedis ->
            with(jedis) {
                hset("$uidPrefix:$uid", "$statusField:$statusValue", statusType.toString())
                hset("$uidPrefix:$uid", "$statusField:$statusUpdateTime", timestamp.toString())
                hset("$uidPrefix:$uid", "$statusField:$statusUser", username)
            }
        }
    }

    fun get(uid: String): UserStatus {
        var userInfo = mutableMapOf<String, String>()
        jedisPool.resource.use {
            userInfo = it.hgetAll("$uidPrefix:$uid")
        }
        if (userInfo.isEmpty()) {
            return UserStatus()
        }
        val statusValue = UserStatusType.valueOf(userInfo["$statusField:$statusValue"]!!)
        val updateTime = userInfo["$statusField:$statusUpdateTime"]!!.toLong()
        val username = userInfo["$statusField:$statusUser"]!!

        return UserStatus(statusValue, username, updateTime)
    }
}
