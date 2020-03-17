package com.microservice.peopleservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class PeopleserviceApplication

fun main(args: Array<String>) {
    runApplication<PeopleserviceApplication>(*args)
}
