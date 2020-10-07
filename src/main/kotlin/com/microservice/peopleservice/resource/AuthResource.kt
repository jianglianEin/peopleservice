package com.microservice.peopleservice.resource

import com.microservice.peopleservice.service.TeamService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class AuthResource {
    @Autowired
    private lateinit var teamService: TeamService

    @PostMapping("/auth/permission")
    fun getPermissionResources(@RequestParam username: String): MutableMap<String, List<String>> {
        val teamIdList = teamService.selectTeamByUsername(username).map { team -> team.id.toString() }
        val peopleServiceClaimMap = mutableMapOf<String, List<String>>()
        peopleServiceClaimMap["teams"] = teamIdList

        return peopleServiceClaimMap
    }
}