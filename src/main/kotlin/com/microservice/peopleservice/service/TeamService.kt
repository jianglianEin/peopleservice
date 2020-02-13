package com.microservice.peopleservice.service

import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.entity.Team
import com.microservice.peopleservice.repository.TeamRepository
import com.microservice.peopleservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeamService {
    @Autowired
    private lateinit var teamRepository: TeamRepository
    @Autowired
    private lateinit var userRepository: UserRepository

    fun createTeam(newTeam: Team): Message {
        userRepository.findByUsername(newTeam.creator!!) ?: return Message(false, "no this user")

        val sameCreatorAndNameTeam = teamRepository.findByCreatorAndTeamname(newTeam.creator!!, newTeam.teamname!!)
        if (sameCreatorAndNameTeam != null) {
            return Message(false, "one creator can not create same name team")
        }

        teamRepository.save(newTeam)
        return Message(true, "team create success")
    }
}