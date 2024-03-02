package com.example.restapiboilerplate.presentation.advice

import com.example.restapiboilerplate.domain.common.exception.BaseException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

private val log = KotlinLogging.logger {}

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        log.warn { e }
        return toErrorResponse(errorType = ErrorType.BAD_REQUEST, message = e.bindingResult.fieldErrors.firstOrNull()?.defaultMessage)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleMismatchedInputException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        log.warn { e }
        val errorMessage = when(val cause = e.cause) {
            is MismatchedInputException -> cause.path.firstOrNull()?.fieldName?.let { "$it 이 null 입니다." }
            else -> null
        }
        return toErrorResponse(errorType = ErrorType.BAD_REQUEST, message = errorMessage)
    }

    @ExceptionHandler(BaseException::class)
    fun handleValidationException(e: BaseException): ResponseEntity<ErrorResponse> {
        log.warn { e }
        return toErrorResponse(errorType = ErrorType.from(e), message = e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.error { e }
        return toErrorResponse(errorType = ErrorType.UNKNOWN, message = e.message)
    }

    private fun toErrorResponse(errorType: ErrorType, message: String? = null): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(errorType.httpStatus)
            .body(
                ErrorResponse(
                    errorCode = errorType.errorCode,
                    message = message ?: errorType.message,
                )
            )
    }

}
