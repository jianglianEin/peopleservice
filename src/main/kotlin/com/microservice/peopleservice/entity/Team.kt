package com.microservice.peopleservice.entity

import javax.persistence.*


@Entity
@Table(name = "team", schema = "public")
class Team() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "creator")
    var creator: String? = null

    @Column(name = "teamname")
    var teamname: String? = ""

    @Column(name = "description")
    var description: String? = null

    constructor(creator: String? = null,
                teamname: String? = null,
                description: String? = "") : this() {
        this.creator = creator
        this.teamname = teamname
        this.description = description
    }
}
