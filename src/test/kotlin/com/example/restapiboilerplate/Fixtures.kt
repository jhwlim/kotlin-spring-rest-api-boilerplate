package com.example.restapiboilerplate

import com.example.restapiboilerplate.domain.user.aggregate.User
import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import com.example.restapiboilerplate.domain.user.value.UserStatus
import java.time.LocalDateTime

fun newUser(
    id: Long = 0L,
    name: String = "테스트",
    email: String = "test1234@test.com",
    password: String = "1234",
    status: UserStatus = UserStatus.JOIN_REQUESTED,
    createdAt: LocalDateTime = LocalDateTime.MIN,
    modifiedAt: LocalDateTime = LocalDateTime.MIN,
) = User(
    id = id,
    name = name,
    email = Email(email),
    password = Password(password),
    status = status,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)

fun newSignUpUserCommand(
    name: String = "테스트",
    email: String = "test1234@test.com",
    password: String = "1234",
) = SignUpUserCommand(
    name = name,
    email = Email(email),
    password = Password(password)
)
