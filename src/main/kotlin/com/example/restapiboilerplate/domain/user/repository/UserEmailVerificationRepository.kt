package com.example.restapiboilerplate.domain.user.repository

import com.example.restapiboilerplate.domain.user.aggregate.UserEmailVerification

interface UserEmailVerificationRepository {

    fun save(userEmailVerification: UserEmailVerification): UserEmailVerification

    fun findById(id: Long): UserEmailVerification

}
