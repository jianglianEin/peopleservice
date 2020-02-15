package com.microservice.peopleservice.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.data.redis.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Configuration
class BeanConfig {
    @Autowired
    private lateinit var redisProperties: RedisProperties

    @SuppressWarnings("MagicNumber")
    @Bean
    fun jedisPool(): JedisPool {
        val jedisPoolConfig = JedisPoolConfig()
        jedisPoolConfig.maxTotal = 100
        val timeout = redisProperties.timeout.toMillis().toInt()
        return JedisPool(jedisPoolConfig, redisProperties.host, redisProperties.port, timeout)
    }
}