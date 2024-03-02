package com.example.restapiboilerplate.domain.common.exception

open class FailureException(type: FailureReasonType) : BaseException(type.message)

interface FailureReasonType {
    val message: String
}
