package com.example.restapiboilerplate.domain.user.exception

data class VerifyUserEmailFailureException(private val type: VerifyUserEmailFailureType) : RuntimeException(type.message)

enum class VerifyUserEmailFailureType(
    val message: String
) {
    NOT_MATCHED_TOKEN("token 정보가 일치하지 않습니다."),
    EXPIRED_TOKEN("이미 만료된 token 입니다."),
    ;
}
