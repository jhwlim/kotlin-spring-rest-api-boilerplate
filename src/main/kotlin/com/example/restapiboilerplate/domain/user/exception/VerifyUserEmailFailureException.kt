package com.example.restapiboilerplate.domain.user.exception

import com.example.restapiboilerplate.domain.common.exception.FailureException
import com.example.restapiboilerplate.domain.common.exception.FailureReasonType

data class VerifyUserEmailFailureException(private val type: VerifyUserEmailFailureReasonType) : FailureException(type)

enum class VerifyUserEmailFailureReasonType(
    override val message: String,
) : FailureReasonType {
    ALREADY_VERIFIED_EMAIL("이미 인증된 이메일 입니다."),
    NOT_MATCHED_TOKEN("token 정보가 일치하지 않습니다."),
    EXPIRED_TOKEN("이미 만료된 token 입니다."),
    ;
}
