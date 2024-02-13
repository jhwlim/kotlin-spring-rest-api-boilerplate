package com.example.restapiboilerplate.domain.user.aggregate

import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import com.example.restapiboilerplate.domain.user.value.UserStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "users")
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val email: Email,
    val name: String,
    val password: Password,
    val status: UserStatus,
    val createdAt: LocalDateTime = LocalDateTime.MIN,
    val modifiedAt: LocalDateTime = LocalDateTime.MIN,
)
