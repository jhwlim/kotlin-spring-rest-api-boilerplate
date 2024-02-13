package com.example.restapiboilerplate

import io.kotest.assertions.throwables.shouldThrowWithMessage

inline fun shouldThrowIllegalArgumentExceptionWithMessage(message: String, block: () -> Any?): IllegalArgumentException = shouldThrowWithMessage<IllegalArgumentException>(message, block)
