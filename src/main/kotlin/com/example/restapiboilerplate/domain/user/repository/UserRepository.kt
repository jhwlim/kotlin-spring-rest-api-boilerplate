package com.example.restapiboilerplate.domain.user.repository

import com.example.restapiboilerplate.domain.user.aggregate.User
import com.example.restapiboilerplate.domain.user.value.Email

interface UserRepository {

    fun save(user: User): User

    fun findAll(): List<User>

    fun findById(id: Long): User?

    fun findByEmail(email: Email): User?

}
