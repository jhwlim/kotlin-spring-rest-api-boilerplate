package com.example.restapiboilerplate.domain.user.model

import com.example.restapiboilerplate.domain.user.aggregate.User
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import com.example.restapiboilerplate.domain.user.value.UserStatus
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpUserCommand(
    val name: String,
    val rawPassword: String,
    val email: Email,
) {

    fun toUser(passwordEncoder: PasswordEncoder): User = User(
        name = name,
        password = Password(passwordEncoder.encode(rawPassword)),
        email = email,
        status = UserStatus.BEFORE_EMAIL_CHECK
    )

}
