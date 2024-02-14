package com.example.restapiboilerplate.application.exception

class ValidationException(
    override val message: String,
) : RuntimeException()
