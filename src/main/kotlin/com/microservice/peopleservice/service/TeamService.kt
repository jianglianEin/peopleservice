package com.microservice.peopleservice.service

import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.entity.Team
import com.microservice.peopleservice.entity.UserTeamRelation
import com.microservice.peopleservice.repository.TeamRepository
import com.microservice.peopleservice.repository.UserRepository
import com.microservice.peopleservice.repository.UserTeamRepository
import mu.KotlinLogging
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

    private var logger = KotlinLogging.logger {}

    fun createTeam(newTeam: Team): Team {
        userRepository.findByUsername(newTeam.creator!!) ?: return Team(description = "no this user")

        val sameCreatorAndNameTeam = teamRepository.findByCreatorAndTeamname(newTeam.creator!!, newTeam.teamname!!)
        if (sameCreatorAndNameTeam != null) {
            logger.warn { "one creator can not create same name team" }
            return Team(description = "one creator can not create same name team")
        }

        val saveTeam = teamRepository.save(newTeam)
        addUserIntoTeam(UserTeamRelation(saveTeam.creator, saveTeam.id))
        return saveTeam
    }

    fun updateTeamById(updateTeam: Team): Team {
        userRepository.findByUsername(updateTeam.creator!!) ?: return Team(description = "no this user")

        val oldTeamOptional = teamRepository.findById(updateTeam.id!!)
        if (oldTeamOptional.isPresent) {
            val oldTeam = oldTeamOptional.get()
            return when {
                oldTeam.teamname != updateTeam.teamname -> {
                    logger.warn { "can not change team name" }
                    Team(description = "can not change team name")
                }
                oldTeam.creator != updateTeam.creator -> {
                    logger.warn { "input creator is error" }
                    Team(description = "input creator is error")
                }
                else -> {
                    oldTeam.description = updateTeam.description
                    teamRepository.save(oldTeam)
                }
            }
        }
        logger.warn { "team id do not exit" }
        return Team(description = "team id do not exit")
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

    fun selectTeamByUsername(username: String): MutableList<Team> {
        val userTeamRelationList = userTeamRepository.findAllByUsername(username)

        val teamList = mutableListOf<Team>()
        for (userTeamRelation in userTeamRelationList){
            val teamOptional = teamRepository.findById(userTeamRelation.teamId!!)

            if (teamOptional.isPresent){
                teamList.add(teamOptional.get())
            }
        }

        return teamList
    }
}