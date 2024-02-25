package com.example.restapiboilerplate.domain.common.exception

open class NotFoundEntityException(override val message: String? = null) : RuntimeException(message)

class NotFoundUserException(userId: Long) : NotFoundEntityException("User 를 찾을 수 없습니다. (id : $userId)")
