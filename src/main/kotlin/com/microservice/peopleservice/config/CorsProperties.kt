package com.microservice.peopleservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class CorsProperties {
    @Value("\${cors.url}")
    lateinit var url: String
}