package com.example.restapiboilerplate

import com.example.restapiboilerplate.presentation.advice.ErrorResponse
import com.fasterxml.jackson.databind.ObjectMapper
import io.kotest.assertions.throwables.shouldThrowWithMessage
import org.springframework.test.web.servlet.MvcResult
import kotlin.reflect.KClass

inline fun shouldThrowIllegalArgumentExceptionWithMessage(message: String, block: () -> Any?): IllegalArgumentException = shouldThrowWithMessage<IllegalArgumentException>(message, block)

fun <T: Any> ObjectMapper.readResponse(result: MvcResult, responseType: KClass<T>): T {
    return this.readValue(result.response.getContentAsString(Charsets.UTF_8), responseType.java)
}

fun ObjectMapper.readErrorResponse(result: MvcResult): ErrorResponse {
    return this.readResponse(result, ErrorResponse::class)
}
