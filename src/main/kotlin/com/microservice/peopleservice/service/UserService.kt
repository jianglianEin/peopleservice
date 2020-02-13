package com.microservice.peopleservice.service

import com.microservice.peopleservice.dto.Message
import com.microservice.peopleservice.entity.User
import com.microservice.peopleservice.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {
    @Autowired
    private lateinit var userRepository: UserRepository

    fun loginByUsernameAndPassword(username: String, password: String): User {
        return userRepository.findByUsernameAndPassword(username, password)
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

    fun updateUserByUsername(updateUser: User): Message {
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
        return when {
            updateResult == null -> Message(false, "username do not exit")
            else -> Message(true, "update user success")
        }
    }
}