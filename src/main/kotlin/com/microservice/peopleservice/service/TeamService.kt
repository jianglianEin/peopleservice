package com.microservice.peopleservice.service

import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.entity.Team
import com.microservice.peopleservice.repository.TeamRepository
import com.microservice.peopleservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

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

    fun updateTeamById(updateTeam: Team): Message {
        userRepository.findByUsername(updateTeam.creator!!) ?: return Message(false, "no this user")

        val oldTeamOptional = teamRepository.findById(updateTeam.id!!)
        if (!oldTeamOptional.isEmpty) {
            val oldTeam = oldTeamOptional.get()

            if (!oldTeam.teamname.equals(updateTeam.teamname)){
                oldTeam.teamname = updateTeam.teamname
                val sameCreatorAndNameTeam = teamRepository.findByCreatorAndTeamname(oldTeam.creator!!, oldTeam.teamname!!)
                if (sameCreatorAndNameTeam != null) {
                    return Message(false, "one creator can not create same name team")
                }
            }
            if (updateTeam.description != null) {
                oldTeam.description = updateTeam.description
            }

            teamRepository.save(oldTeam)
            return Message(true, "update user success")
        }

        return Message(false, "team id do not exit")
    }
}