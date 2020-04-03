package com.microservice.peopleservice.resource

import com.microservice.peopleservice.config.EnvProperties
import com.microservice.peopleservice.entity.User
import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class UserResource {
    @Autowired
    lateinit var env: EnvProperties

    @Autowired
    private lateinit var userService: UserService

    @GetMapping()
    fun hello(): String {
        System.out.println("run in PeopleService")
        System.out.println(env.env)
        return "hello world\n" + env.env
    }

    @PostMapping("/user/login")
    fun login(@RequestParam username: String, @RequestParam password: String, @RequestParam uid: String): User {
        return userService.login(username, password, uid)
    }

    @PostMapping("/user/logout")
    fun logout(@RequestParam username: String, @RequestParam uid: String): Message {
        return userService.logout(username, uid)
    }

    @PostMapping("/user/register")
    fun register(@RequestParam username: String,
                 @RequestParam password: String,
                 @RequestParam email: String): Message {
        val newUser = User(username = username, password = password, email = email)

        return userService.createUser(newUser)
    }

    @PostMapping("/user/update")
    fun updateUser(@RequestParam username: String?,
                   @RequestParam password: String?,
                   @RequestParam icon: String?,
                   @RequestParam power: Int?): User {
        val updateUser = User(username = username, password = password, icon = icon, power = power)

        return userService.updateUserByUsername(updateUser)
    }

    @PostMapping("/user/select")
    fun selectUser(@RequestParam inputName: String): MutableList<User>? {

        return userService.selectUserByUsernameSubstring(inputName)
    }

    @PostMapping("/user/selectByName")
    fun selectUserByName(@RequestParam username: String): User {

        return userService.selectUserByUsername(username)
    }

    @PostMapping("/user/selectByTeam")
    fun selectPeopleByTeam(@RequestParam teamId: String): MutableList<User>{

        return userService.selectPeopleByTeam(teamId)
    }
}