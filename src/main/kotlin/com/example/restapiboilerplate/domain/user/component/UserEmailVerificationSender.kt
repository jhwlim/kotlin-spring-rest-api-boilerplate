package com.example.restapiboilerplate.domain.user.component

import com.example.restapiboilerplate.domain.user.aggregate.UserEmailVerification

interface UserEmailVerificationSender {

    fun sendUserEmailVerification(userEmailVerification: UserEmailVerification)

}
