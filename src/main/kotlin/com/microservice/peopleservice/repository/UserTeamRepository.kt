package com.microservice.peopleservice.repository

import com.microservice.peopleservice.entity.UserTeamRelation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserTeamRepository: JpaRepository<UserTeamRelation, Int> {
    fun findByUsernameAndTeamId(username: String, teamId: Int): UserTeamRelation?
    fun findAllByUsername(username: String): MutableList<UserTeamRelation>
    fun findAllByTeamId(teamId: Int): MutableList<UserTeamRelation>
}