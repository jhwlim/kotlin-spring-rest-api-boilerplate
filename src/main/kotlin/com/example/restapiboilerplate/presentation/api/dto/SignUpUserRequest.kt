package com.example.restapiboilerplate.presentation.api.dto

import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import jakarta.validation.constraints.NotBlank

data class SignUpUserRequest(
    @field:NotBlank(message = "name 은 비어있을 수 없습니다.")
    val name: String,
    @field:NotBlank(message = "email 은 비어있을 수 없습니다.")
    @field:jakarta.validation.constraints.Email(message = "email 형식이 올바르지 않습니다.")
    val email: String,
    @field:NotBlank(message = "password 는 비어있을 수 없습니다.")
    val password: String,
) {

    fun toSignUpUserCommand(): SignUpUserCommand {
        return SignUpUserCommand(
            name = name,
            email = Email(email),
            password = Password(password),
        )
    }

}
