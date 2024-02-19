package com.example.restapiboilerplate

import com.example.restapiboilerplate.domain.user.value.UserStatus
import java.time.LocalDateTime

object TestConstants {

    const val DEFAULT_USER_ID = 1L
    const val DEFAULT_USER_NAME = "테스트"
    const val DEFAULT_USER_EMAIL_VALUE = "test1234@test.com"
    const val DEFAULT_USER_PASSWORD_VALUE = "1234"

    val DEFAULT_USER_STATUS: UserStatus = UserStatus.BEFORE_EMAIL_CHECK
    val DEFAULT_CREATED_AT: LocalDateTime = LocalDateTime.MIN
    val DEFAULT_MODIFIED_AT: LocalDateTime = LocalDateTime.MIN

    const val DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE = "testUserEmailVerificationToken"

    val DEFAULT_DATE_TIME: LocalDateTime = LocalDateTime.of(2024, 1, 1, 0, 0, 0)

}
