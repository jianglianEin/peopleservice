package com.microservice.peopleservice.repository

import com.microservice.peopleservice.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Int> {
    fun findByUsernameAndPassword(username: String, password: String): User
    fun findByUsernameOrEmail(username: String, email: String): User?
    fun save(user: User): User
    fun findByUsername(username: String): User?
}