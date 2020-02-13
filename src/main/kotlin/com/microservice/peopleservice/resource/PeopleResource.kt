package com.microservice.peopleservice.resource

import com.microservice.peopleservice.config.EnvProperties
import com.microservice.peopleservice.entity.User
import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class PeopleResource {
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

    @GetMapping("/user/login")
    fun login(@RequestParam username: String, @RequestParam password: String): User {
        return userService.loginByUsernameAndPassword(username, password)
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
                   @RequestParam power: Int?): Message {
        val updateUser = User(username = username, password = password, icon = icon, power = power)

        return userService.updateUserByUsername(updateUser)
    }

    @GetMapping("/user/select")
    fun selectUser(@RequestParam inputName: String): MutableList<User>? {

        return userService.selectUserByUsernameSubstring(inputName)
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