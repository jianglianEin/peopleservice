package com.microservice.peopleservice.resource

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PeopleResource() {

    @GetMapping()
    fun hello(): String {
        System.out.println("run in PeopleService")
        return "hello world"
    }
}