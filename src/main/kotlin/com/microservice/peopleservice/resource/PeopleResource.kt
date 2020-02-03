package com.microservice.peopleservice.resource

import com.microservice.peopleservice.dao.User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PeopleResource() {

    @GetMapping()
    fun hello(): String {
        System.out.println("run in PeopleService")
        return "hello world"
    }

    @GetMapping("/user/login")
    fun login(): User {
        return User("jianglianEin","","/icon/image.jpg", "913057041@qq.com", 1)
    }
}