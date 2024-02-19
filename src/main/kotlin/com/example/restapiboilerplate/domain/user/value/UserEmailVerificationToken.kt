package com.example.restapiboilerplate.domain.user.value

import java.util.*

@JvmInline
value class UserEmailVerificationToken(
    private val value: String = UUID.randomUUID().toString()
)
