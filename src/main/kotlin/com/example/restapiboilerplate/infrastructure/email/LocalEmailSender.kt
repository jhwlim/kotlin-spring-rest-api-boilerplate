package com.example.restapiboilerplate.infrastructure.email

import com.example.restapiboilerplate.domain.user.aggregate.UserEmailVerification
import com.example.restapiboilerplate.domain.user.component.UserEmailVerificationSender
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Component

private val log = KotlinLogging.logger {}

@Component
class LocalEmailSender : UserEmailVerificationSender {

    override fun sendUserEmailVerification(userEmailVerification: UserEmailVerification) {
        val userId = userEmailVerification.user.id
        val token = userEmailVerification.token

        log.info { "path : /v1/users/$userId/verifyEmail?token=$token" }
    }

}
