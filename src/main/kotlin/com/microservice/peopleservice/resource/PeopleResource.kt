package com.microservice.peopleservice.resource

import com.microservice.peopleservice.config.EnvProperties
import com.microservice.peopleservice.entity.User
import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.entity.Team
import com.microservice.peopleservice.service.TeamService
import com.microservice.peopleservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class PeopleResource {
    @Autowired
    lateinit var env: EnvProperties

    @Autowired
    private lateinit var userService: UserService
    @Autowired
    private lateinit var teamService: TeamService

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
    fun createTeam(@RequestParam teamname: String,
                   @RequestParam description: String,
                   @RequestParam creator: String): Message {
        val newTeam = Team(creator, teamname, description)

        return teamService.createTeam(newTeam)
    }

    @PostMapping("/team/handleUser")
    fun modifyUserInTeam(@RequestParam teamId: Int,
                         @RequestParam username: String,
                         @RequestParam isAdd: Boolean): Message {

        return teamService.modifyUserWithTeamHandler(teamId, username, isAdd)
    }

    @PostMapping("/team/update")
    fun updateTeam(@RequestParam id: Int,
                   @RequestParam creator: String,
                   @RequestParam teamname: String,
                   @RequestParam description: String): Message {
        val updateTeam = Team(creator, teamname, description)
        updateTeam.id = id

        return teamService.updateTeamById(updateTeam)
    }

    @PostMapping("/team/remove")
    fun removeTeam(@RequestParam id: Int): Message {
        return teamService.removeTeamById(id)
    }
}