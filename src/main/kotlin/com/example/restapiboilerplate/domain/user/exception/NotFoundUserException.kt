package com.example.restapiboilerplate.domain.user.exception

import com.example.restapiboilerplate.domain.common.exception.NotFoundEntityException

data class NotFoundUserException(private val userId: Long) : NotFoundEntityException("User 를 찾을 수 없습니다. (id : $userId)")
