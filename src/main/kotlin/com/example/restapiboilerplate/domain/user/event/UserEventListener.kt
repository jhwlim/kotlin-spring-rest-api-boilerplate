package com.example.restapiboilerplate.domain.user.event

import com.example.restapiboilerplate.domain.common.exception.NotFoundUserException
import com.example.restapiboilerplate.domain.user.aggregate.UserEmailVerification
import com.example.restapiboilerplate.domain.user.component.UserEmailVerificationSender
import com.example.restapiboilerplate.domain.user.repository.UserEmailVerificationRepository
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class UserEventListener(
    private val userRepository: UserRepository,
    private val userEmailVerificationRepository: UserEmailVerificationRepository,
    private val userEmailVerificationSender: UserEmailVerificationSender,
) {

    @Async
    @EventListener
    @Transactional
    fun handleUserSignedUpEvent(event: UserSignedUpEvent) {
        val (userId) = event
        val user = userRepository.findById(userId) ?: throw NotFoundUserException(userId)
        val userEmailVerification = userEmailVerificationRepository.save(
            UserEmailVerification(
                user = user,
                token = UserEmailVerificationToken(),
                publishedAt = LocalDateTime.now(),
            )
        )

        userEmailVerificationSender.sendUserEmailVerification(userEmailVerification)
    }

}
