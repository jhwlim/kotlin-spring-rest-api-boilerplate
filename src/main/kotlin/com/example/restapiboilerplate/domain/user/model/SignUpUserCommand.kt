package com.example.restapiboilerplate.domain.user.model

import com.example.restapiboilerplate.domain.user.aggregate.User
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import com.example.restapiboilerplate.domain.user.value.UserStatus

data class SignUpUserCommand(
    val name: String,
    val password: Password,
    val email: Email,
) {

    fun toUser(): User = User(
        name = name,
        password = password,
        email = email,
        status = UserStatus.JOIN_REQUESTED
    )

}
