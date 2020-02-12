package com.microservice.peopleservice.entity

import javax.persistence.*


@Entity
@Table(name = "user", schema = "public")
class User() {
    @Id
    @GeneratedValue
    @Column(name = "username")
    var username: String? = null

    @Column(name = "password")
    var password: String? = null

    @Column(name = "icon")
    var icon: String? = null

    @Column(name = "email")
    var email: String? = null

    @Column(name = "right")
    var right: Int? = 1

    constructor(username: String? = null,
                password: String? = null,
                icon: String? = null,
                email: String? = null,
                right: Int? = 1) : this() {
        this.username = username
        this.password = password
        this.icon = icon
        this.email = email
        this.right = right
    }
}
