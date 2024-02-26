package com.example.restapiboilerplate.domain.user.repository

import com.example.restapiboilerplate.domain.user.aggregate.UserEmailVerification
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken

interface UserEmailVerificationRepository {

    fun save(userEmailVerification: UserEmailVerification): UserEmailVerification

    fun findById(id: Long): UserEmailVerification

    fun findByToken(token: UserEmailVerificationToken): UserEmailVerification?

}
