package com.microservice.peopleservice.repository

import com.microservice.peopleservice.entity.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, String> {
    fun  findByUsernameAndPassword(username: String, password: String): User
}