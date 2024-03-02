package com.example.restapiboilerplate.presentation.advice

import com.example.restapiboilerplate.application.exception.ValidationException
import com.example.restapiboilerplate.domain.common.exception.BaseException
import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

enum class ErrorType(
    code: Int,
    val message: String,
    val httpStatus: HttpStatus,
    private val exceptions: List<KClass<out BaseException>> = emptyList()
) {

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
