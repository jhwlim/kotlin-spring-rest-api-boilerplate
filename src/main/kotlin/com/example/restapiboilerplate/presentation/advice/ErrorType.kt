package com.example.restapiboilerplate.presentation.advice

import org.springframework.http.HttpStatus

enum class ErrorType(
    code: Int,
    val message: String,
    val httpStatus: HttpStatus,
) {

    BAD_REQUEST(901, "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    UNKNOWN(999, "알 수 없는 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ;

    val errorCode: String = "ERR-$code"

}
