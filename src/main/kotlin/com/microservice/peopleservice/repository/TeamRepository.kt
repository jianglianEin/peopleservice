package com.microservice.peopleservice.repository

import com.microservice.peopleservice.entity.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TeamRepository: JpaRepository<Team, Int> {
    fun findByCreatorAndTeamname(creator: String, teamname: String): Team?
}