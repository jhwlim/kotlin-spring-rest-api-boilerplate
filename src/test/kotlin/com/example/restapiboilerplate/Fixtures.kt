package com.example.restapiboilerplate

import com.example.restapiboilerplate.application.dto.UserDto
import com.example.restapiboilerplate.domain.user.aggregate.User
import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import com.example.restapiboilerplate.domain.user.value.UserStatus
import com.example.restapiboilerplate.presentation.api.dto.UserResponse
import java.time.LocalDateTime

private const val defaultUserId = 1L
private const val defaultUserName = "테스트"
private const val defaultUserEmailValue = "test1234@test.com"
private const val defaultUserPasswordValue = "1234"
private val defaultUserStatus = UserStatus.JOIN_REQUESTED
private val defaultCreatedAt = LocalDateTime.MIN
private val defaultModifiedAt = LocalDateTime.MIN

class SignUpUserRequestWithNull(
    val name: String?,
    val email: String?,
    val password: String?,
)

fun newUser(
    id: Long = 0L,
    name: String = defaultUserName,
    email: String = defaultUserEmailValue,
    password: String = defaultUserPasswordValue,
    status: UserStatus = defaultUserStatus,
    createdAt: LocalDateTime = defaultCreatedAt,
    modifiedAt: LocalDateTime = defaultModifiedAt,
) = User(
    id = id,
    name = name,
    email = Email(email),
    password = Password(password),
    status = status,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)

fun newUserDto(
    id: Long = defaultUserId,
    name: String = defaultUserName,
    email: String = defaultUserEmailValue,
    password: String = defaultUserPasswordValue,
    status: UserStatus = defaultUserStatus,
    createdAt: LocalDateTime = defaultCreatedAt,
    modifiedAt: LocalDateTime = defaultModifiedAt,
) = UserDto(
    id = id,
    name = name,
    email = Email(email),
    password = Password(password),
    status = status,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)

fun newSignUpUserCommand(
    name: String = defaultUserName,
    email: String = defaultUserEmailValue,
    password: String = defaultUserPasswordValue,
) = SignUpUserCommand(
    name = name,
    email = Email(email),
    password = Password(password)
)

fun newSignUpUserRequest(
    name: String? = defaultUserName,
    email: String? = defaultUserEmailValue,
    password: String? = defaultUserPasswordValue,
) = SignUpUserRequestWithNull(
    name = name,
    email = email,
    password = password,
)

fun newUserResponse(
    id: Long = defaultUserId,
    name: String = defaultUserName,
    email: String = defaultUserEmailValue,
    status: UserStatus = defaultUserStatus,
    createdAt: LocalDateTime = defaultCreatedAt,
    modifiedAt: LocalDateTime = defaultModifiedAt,
) = UserResponse(
    id = id,
    name = name,
    email = Email(email),
    status = status,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)
