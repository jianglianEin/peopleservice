package com.microservice.peopleservice.entity

import javax.persistence.*


@Entity
@Table(name = "user", schema = "public")
class User() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Int? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "icon")
    var icon: String? = ""

    @Column(name = "password")
    var password: String? = null

    @Column(name = "power")
    var power: Int? = 1

    @Column(name = "username")
    var username: String? = null

    constructor(username: String? = null,
                password: String? = null,
                icon: String? = "",
                email: String? = null,
                power: Int? = 1) : this() {
        this.username = username
        this.password = password
        this.icon = icon
        this.email = email
        this.power = power
    }
}
