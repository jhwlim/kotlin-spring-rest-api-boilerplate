package com.example.restapiboilerplate.domain.common.exception

open class NotFoundEntityException(override val message: String? = null) : RuntimeException(message)
