package com.microservice.peopleservice.service

import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.entity.User
import com.microservice.peopleservice.poko.type.UserStatusType
import com.microservice.peopleservice.repository.UserRepository
import com.microservice.peopleservice.repository.UserStatusRepository
import com.microservice.peopleservice.repository.UserTeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository
    @Autowired
    private lateinit var userStatusRepository: UserStatusRepository
    @Autowired
    private lateinit var userTeamRepository: UserTeamRepository

    fun login(username: String, password: String): User {
        val loginUser = userRepository.findByUsernameAndPassword(username, password) ?: return User()

        if (UserStatusType.Offline == userStatusRepository.get(username).value) {
            val currentTime = System.currentTimeMillis()
            userStatusRepository.update(UserStatusType.Online, username, currentTime)
        }
        return loginUser
    }

    fun logout(username: String): Message {
        if (userRepository.findByUsername(username) == null) {
            return Message(false, "username do not exit")
        }

        val currentTime = System.currentTimeMillis()
        userStatusRepository.update(UserStatusType.Offline, username, currentTime)
        return Message(true, "logout success")
    }

    fun createUser(newUse: User): Message {
        val sameNameOrEmailUser = userRepository.findByUsernameOrEmail(newUse.username!!, newUse.email!!)
        return when {
            sameNameOrEmailUser == null -> {
                userRepository.save(newUse)
                Message(true, "new user create success")
            }
            sameNameOrEmailUser.username.equals(newUse.username) -> {
                Message(false, "user have same username")
            }
            sameNameOrEmailUser.email.equals(newUse.email) -> {
                Message(false, "user have same email")
            }
            else -> Message(false, "unknown error")
        }
    }

    fun updateUserByUsername(updateUser: User): User {
        val oldUser = updateUser.username?.let {username -> userRepository.findByUsername(username) }

        if (oldUser != null) {
            when {
                updateUser.password != null -> oldUser.password = updateUser.password
            }
            when {
                updateUser.icon != null -> oldUser.icon = updateUser.icon
            }
            when {
                updateUser.power != null -> oldUser.power = updateUser.power
            }
        }

        val updateResult = oldUser?.let {saveUser -> userRepository.save(saveUser) }
        return when (updateResult) {
            null -> User()
            else -> updateResult
        }
    }

    fun selectUserByUsernameSubstring(inputName: String): MutableList<User>? {
        val containInputNameUserList = userRepository.findByUsernameContains(inputName)
        containInputNameUserList?.map {
            it.password = ""
        }

        return containInputNameUserList
    }

    fun selectUserByUsername(username: String): User {
        val user = userRepository.findByUsername(username)
        return when {
            user != null -> user
            else -> User()
        }
    }

    fun selectPeopleByTeam(teamId: String): MutableList<User> {
        val people = mutableListOf<User>()
       val userTeamRelations = userTeamRepository.findAllByTeamId(teamId.toInt())
        for (userTeamRelation in userTeamRelations){
            val userInTeam = userRepository.findByUsername(userTeamRelation.username!!)
            if (userInTeam != null){
                people.add(userInTeam)
            }
        }
        return people
    }
}