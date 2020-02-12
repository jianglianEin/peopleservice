package com.microservice.peopleservice.service

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
}