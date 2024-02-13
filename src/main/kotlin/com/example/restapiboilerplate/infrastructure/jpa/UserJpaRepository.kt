package com.example.restapiboilerplate.infrastructure.jpa

import com.example.restapiboilerplate.domain.user.aggregate.User
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<User, Long>, UserRepository
