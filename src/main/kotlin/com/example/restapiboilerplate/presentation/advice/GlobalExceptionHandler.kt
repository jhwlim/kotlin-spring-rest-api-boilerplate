package com.example.restapiboilerplate.presentation.advice

import com.example.restapiboilerplate.application.exception.ValidationException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
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

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(e: ValidationException): ResponseEntity<ErrorResponse> {
        log.warn { e }
        return toErrorResponse(errorType = ErrorType.BAD_REQUEST, message = e.message)
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
