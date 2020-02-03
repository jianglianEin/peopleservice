package com.microservice.peopleservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PeopleserviceApplication

fun main(args: Array<String>) {
    runApplication<PeopleserviceApplication>(*args)
}
