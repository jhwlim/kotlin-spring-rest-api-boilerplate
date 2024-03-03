package com.example.restapiboilerplate.domain.user.exception

import com.example.restapiboilerplate.domain.common.exception.FailureException
import com.example.restapiboilerplate.domain.common.exception.FailureReasonType
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureReasonType.*

open class VerifyUserEmailFailureException(type: VerifyUserEmailFailureReasonType) : FailureException(type)

class AlreadyVerifiedEmailException : VerifyUserEmailFailureException(ALREADY_VERIFIED_EMAIL)

class UserEmailTokenNotMatchedException : VerifyUserEmailFailureException(NOT_MATCHED_TOKEN)

class UserEmailTokenExpiredException : VerifyUserEmailFailureException(EXPIRED_TOKEN)

enum class VerifyUserEmailFailureReasonType(
    override val message: String,
) : FailureReasonType {
    ALREADY_VERIFIED_EMAIL("이미 인증된 이메일 입니다."),
    NOT_MATCHED_TOKEN("token 정보가 일치하지 않습니다."),
    EXPIRED_TOKEN("이미 만료된 token 입니다."),
    ;
}
