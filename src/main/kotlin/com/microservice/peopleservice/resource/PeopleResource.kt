package com.microservice.peopleservice.resource

import com.microservice.peopleservice.config.EnvProperties
import com.microservice.peopleservice.dao.User
import com.microservice.peopleservice.dto.Message
import org.springframework.beans.factory.annotation.Autowired


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PeopleResource {
    @Autowired
    lateinit var env: EnvProperties

    @GetMapping()
    fun hello(): String {
        System.out.println("run in PeopleService")
        System.out.println(env.env)
        return "hello world\n" + env.env
    }

    @GetMapping("/user/login")
    fun login(@PathVariable username: String, @PathVariable password: String): User {
        return User("jianglianEin","","/icon/image.jpg", "913057041@qq.com", 1)
    }

    @PostMapping("/user/register")
    fun register(): Message {
        return Message(true, "new user create success")
    }

    @PostMapping("/user/update")
    fun updateUser(): Message {
        return Message(true, "update user success")
    }

    @GetMapping("/user/select")
    fun selectUser(@PathVariable username: String): User {
        return User("jianglianEin","","/icon/image.jpg", "913057041@qq.com", 1)
    }

    @PostMapping("/team/create")
    fun createTeam(@PathVariable teamName: String,
                   @PathVariable description: String,
                   @PathVariable creator: String): Message {
        return Message(true, "team create success")
    }

    @PostMapping("/team/handleUser")
    fun handleUserInTeam(@PathVariable teamName: String,
                         @PathVariable creator: String,
                         @PathVariable isRemove: Boolean): Message {
        return Message(true, "user deal with success")
    }

    @PostMapping("/team/update")
    fun updateTeam(): Message {
        return Message(true, "update team success")
    }

    @PostMapping("/team/remove")
    fun removeTeam(): Message {
        return Message(true, "remove team success")
    }
}