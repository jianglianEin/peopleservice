package com.microservice.peopleservice.entity

import javax.persistence.*

@Entity
@Table(name = "user_team_relation", schema = "public")
class UserTeamRelation() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "username")
    var username: String? = null

    @Column(name = "team_id")
    var teamId: Int? = null

    constructor(username: String? = null,
                teamId: Int? = null) : this() {
        this.username = username
        this.teamId = teamId
    }
}