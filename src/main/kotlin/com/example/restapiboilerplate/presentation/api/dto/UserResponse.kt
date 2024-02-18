package com.example.restapiboilerplate.presentation.api.dto

import com.example.restapiboilerplate.application.dto.UserDto
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.UserStatus
import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val name: String,
    val email: Email,
    val status: UserStatus,
    val createdAt: LocalDateTime,
    val modifiedAt: LocalDateTime,
) {

    companion object {

        fun from(user: UserDto): UserResponse {
            return with(user) {
                UserResponse(
                    id = id,
                    name = name,
                    email = email,
                    status = status,
                    createdAt = createdAt,
                    modifiedAt = modifiedAt,
                )
            }
        }

    }

}
