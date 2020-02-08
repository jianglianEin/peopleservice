package com.microservice.peopleservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = ["classpath:application*"], ignoreResourceNotFound = true)
class CorsProperties {
    @Value("\${cors.url}")
    lateinit var url: String
}