package com.example.restapiboilerplate.application.dto

import com.example.restapiboilerplate.domain.user.aggregate.User
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import com.example.restapiboilerplate.domain.user.value.UserStatus
import java.time.LocalDateTime

data class UserDto(
    val id: Long,
    val name: String,
    val password: Password,
    val email: Email,
    val status: UserStatus,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {

    companion object {

        fun from(user: User): UserDto {
            return with(user) {
                UserDto(
                    id = id,
                    name = name,
                    password = password,
                    email = email,
                    status = status,
                    createdAt = createdAt,
                    modifiedAt = modifiedAt,
                )
            }
        }

    }

}
