package com.microservice.peopleservice.service

import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.entity.Team
import com.microservice.peopleservice.entity.UserTeamRelation
import com.microservice.peopleservice.repository.TeamRepository
import com.microservice.peopleservice.repository.UserRepository
import com.microservice.peopleservice.repository.UserTeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeamService {
    @Autowired
    private lateinit var teamRepository: TeamRepository
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var userTeamRepository: UserTeamRepository

    fun createTeam(newTeam: Team): Message {
        userRepository.findByUsername(newTeam.creator!!) ?: return Message(false, "no this user")

        val sameCreatorAndNameTeam = teamRepository.findByCreatorAndTeamname(newTeam.creator!!, newTeam.teamname!!)
        if (sameCreatorAndNameTeam != null) {
            return Message(false, "one creator can not create same name team")
        }

        val newTeamCreateSuccess = teamRepository.save(newTeam)
        addUserIntoTeam(UserTeamRelation(newTeamCreateSuccess.creator, newTeamCreateSuccess.id))
        return Message(true, "teamId: ${newTeamCreateSuccess.id}")
    }

    fun updateTeamById(updateTeam: Team): Message {
        userRepository.findByUsername(updateTeam.creator!!) ?: return Message(false, "no this user")

        val oldTeamOptional = teamRepository.findById(updateTeam.id!!)
        if (oldTeamOptional.isPresent) {
            val oldTeam = oldTeamOptional.get()
            return when {
                oldTeam.teamname != updateTeam.teamname -> {
                    Message(false, "can not change team name")
                }
                oldTeam.creator != updateTeam.creator -> {
                    Message(false, "input creator is error")
                }
                else -> {
                    oldTeam.description = updateTeam.description
                    teamRepository.save(oldTeam)
                    Message(true, "update team success")
                }
            }
        }

        return Message(false, "team id do not exit")
    }

    fun removeTeamById(id: Int): Message {
        val teamOption = teamRepository.findById(id)
        if (teamOption.isPresent) {
            teamRepository.deleteById(id)
            return Message(true, "team remove success")
        }
        return Message(true, "team id do not exit")
    }

    fun modifyUserWithTeamHandler(teamId: Int, username: String, add: Boolean): Message {
        val checkMessage = checkUsernameAndTeamIdIsValidity(username, teamId)
        if (!checkMessage.isSusses){
            return checkMessage
        }

        val newUserTeamRelation = UserTeamRelation(username, teamId)
        return when {
            add -> addUserIntoTeam(newUserTeamRelation)
            else -> removeUserOutTeam(newUserTeamRelation)
        }
    }

    private fun checkUsernameAndTeamIdIsValidity(username: String, teamId: Int): Message {
        userRepository.findByUsername(username) ?: return Message(false, "no this user")
        val modifyTeamOption = teamRepository.findById(teamId)
        return when {
            modifyTeamOption.isEmpty -> Message(false, "team id do not exit")
            modifyTeamOption.get().creator.equals(username) -> Message(false, "team can not modify creator by this function")
            else -> Message(true, "ok")
        }
    }

    private fun removeUserOutTeam(userTeamRelation: UserTeamRelation): Message {
        return when (val userTeamRelationInDB =
                userTeamRepository.findByUsernameAndTeamId(userTeamRelation.username!!, userTeamRelation.teamId!!)) {
            null -> {
                Message(false, "user do not in this team")
            }
            else -> {
                userTeamRepository.delete(userTeamRelationInDB)
                Message(true, "remove user success")
            }
        }
    }

    private fun addUserIntoTeam(userTeamRelation: UserTeamRelation): Message {
        return when {
            userTeamRepository.findByUsernameAndTeamId(userTeamRelation.username!!, userTeamRelation.teamId!!) != null -> {
                Message(false, "user already in this team")
            }
            else -> {
                userTeamRepository.save(userTeamRelation)
                Message(true, "add user success")
            }
        }
    }
}