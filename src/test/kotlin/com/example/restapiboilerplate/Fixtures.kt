package com.example.restapiboilerplate

import com.example.restapiboilerplate.TestConstants.DEFAULT_CREATED_AT
import com.example.restapiboilerplate.TestConstants.DEFAULT_DATE_TIME
import com.example.restapiboilerplate.TestConstants.DEFAULT_MODIFIED_AT
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_EMAIL_VALUE
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_ID
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_NAME
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_PASSWORD_VALUE
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_STATUS
import com.example.restapiboilerplate.application.dto.UserDto
import com.example.restapiboilerplate.domain.user.aggregate.User
import com.example.restapiboilerplate.domain.user.aggregate.UserEmailVerification
import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import com.example.restapiboilerplate.domain.user.value.UserStatus
import com.example.restapiboilerplate.presentation.api.dto.UserResponse
import java.time.LocalDateTime

class SignUpUserRequestWithNull(
    val name: String?,
    val email: String?,
    val password: String?,
)

fun newUser(
    id: Long = 0L,
    name: String = DEFAULT_USER_NAME,
    email: String = DEFAULT_USER_EMAIL_VALUE,
    password: String = DEFAULT_USER_PASSWORD_VALUE,
    status: UserStatus = DEFAULT_USER_STATUS,
    createdAt: LocalDateTime = DEFAULT_CREATED_AT,
    modifiedAt: LocalDateTime = DEFAULT_MODIFIED_AT,
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
    id: Long = DEFAULT_USER_ID,
    name: String = DEFAULT_USER_NAME,
    email: String = DEFAULT_USER_EMAIL_VALUE,
    password: String = DEFAULT_USER_PASSWORD_VALUE,
    status: UserStatus = DEFAULT_USER_STATUS,
    createdAt: LocalDateTime = DEFAULT_CREATED_AT,
    modifiedAt: LocalDateTime = DEFAULT_MODIFIED_AT,
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
    name: String = DEFAULT_USER_NAME,
    email: String = DEFAULT_USER_EMAIL_VALUE,
    password: String = DEFAULT_USER_PASSWORD_VALUE,
) = SignUpUserCommand(
    name = name,
    email = Email(email),
    rawPassword = password,
)

fun newSignUpUserRequest(
    name: String? = DEFAULT_USER_NAME,
    email: String? = DEFAULT_USER_EMAIL_VALUE,
    password: String? = DEFAULT_USER_PASSWORD_VALUE,
) = SignUpUserRequestWithNull(
    name = name,
    email = email,
    password = password,
)

fun newUserResponse(
    id: Long = DEFAULT_USER_ID,
    name: String = DEFAULT_USER_NAME,
    email: String = DEFAULT_USER_EMAIL_VALUE,
    status: UserStatus = DEFAULT_USER_STATUS,
    createdAt: LocalDateTime = DEFAULT_CREATED_AT,
    modifiedAt: LocalDateTime = DEFAULT_MODIFIED_AT,
) = UserResponse(
    id = id,
    name = name,
    email = Email(email),
    status = status,
    createdAt = createdAt,
    modifiedAt = modifiedAt,
)

fun newUserEmailVerification(
    user: User,
    token: String = DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE,
    expiredAt: LocalDateTime = DEFAULT_DATE_TIME,
    verifiedAt: LocalDateTime? = null,
): UserEmailVerification {
    return UserEmailVerification(
        token = UserEmailVerificationToken(token),
        expiredAt = expiredAt,
        user = user,
        verifiedAt = verifiedAt,
    )
}
