package com.example.restapiboilerplate.presentation.advice

import com.example.restapiboilerplate.application.exception.ValidationException
import com.example.restapiboilerplate.domain.common.exception.BaseException
import com.example.restapiboilerplate.domain.user.exception.AlreadyVerifiedEmailException
import com.example.restapiboilerplate.domain.user.exception.UserEmailTokenExpiredException
import com.example.restapiboilerplate.domain.user.exception.UserEmailTokenNotMatchedException
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureException
import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

enum class ErrorType(
    code: Int,
    val message: String,
    val httpStatus: HttpStatus,
    private val exceptions: List<KClass<out BaseException>> = emptyList()
) {

    VERIFY_USER_EMAIL_FAILED(120, "이메일 인증에 실패하였습니다.", HttpStatus.BAD_REQUEST, listOf(
        VerifyUserEmailFailureException::class,
    )),
    ALREADY_VERIFIED_USER_EMAIL(121, "이미 인증된 이메일입니다.", HttpStatus.BAD_REQUEST, listOf(
        AlreadyVerifiedEmailException::class,
    )),
    USER_EMAIL_TOKEN_NOT_MATCHED(122, "token 정보가 일치하지 않습니다.", HttpStatus.BAD_REQUEST, listOf(
        UserEmailTokenNotMatchedException::class,
    )),
    USER_EMAIL_TOKEN_EXPIRED(123, "이미 만료된 token 입니다.", HttpStatus.BAD_REQUEST, listOf(
        UserEmailTokenExpiredException::class,
    )),

    BAD_REQUEST(901, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST, listOf(
        ValidationException::class,
    )),
    UNKNOWN(999, "알 수 없는 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    val errorCode: String = "ERR-$code"

    companion object {

        fun from(exception: BaseException): ErrorType {
            return entries.firstOrNull { exception::class in it.exceptions } ?: UNKNOWN
        }

    }

}
