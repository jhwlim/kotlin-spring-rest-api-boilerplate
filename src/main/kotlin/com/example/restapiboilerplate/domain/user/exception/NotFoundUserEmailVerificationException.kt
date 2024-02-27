package com.example.restapiboilerplate.domain.user.exception

import com.example.restapiboilerplate.domain.common.exception.NotFoundEntityException

data class NotFoundUserEmailVerificationException(private val userId: Long) : NotFoundEntityException("UserEmailVerification 을 찾을 수 없습니다. (userId : $userId)")
