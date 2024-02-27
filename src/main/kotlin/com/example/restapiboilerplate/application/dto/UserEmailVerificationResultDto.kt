package com.example.restapiboilerplate.application.dto

import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureException
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureType

data class UserEmailVerificationResultDto(
    val verified: Boolean,
    val failureType: VerifyUserEmailFailureType? = null,
) {

    companion object {

        fun success() = UserEmailVerificationResultDto(verified = true)

        fun failure(e: VerifyUserEmailFailureException) = UserEmailVerificationResultDto(verified = false, failureType = e.type)

    }

}
