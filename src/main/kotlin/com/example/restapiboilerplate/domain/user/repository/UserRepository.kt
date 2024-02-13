package com.example.restapiboilerplate.domain.user.repository

import com.example.restapiboilerplate.domain.user.aggregate.User

interface UserRepository {

    fun save(user: User): User

    fun findAll(): List<User>

}
