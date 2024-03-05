package com.example.restapiboilerplate.application

import com.example.restapiboilerplate.application.dto.UserDto
import com.example.restapiboilerplate.application.validator.UserValidator
import com.example.restapiboilerplate.domain.user.event.UserSignedUpEvent
import com.example.restapiboilerplate.domain.user.exception.NotFoundUserEmailVerificationException
import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.repository.UserEmailVerificationRepository
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val userValidator: UserValidator,
    private val userRepository: UserRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val passwordEncoder: PasswordEncoder,
    private val userEmailVerificationRepository: UserEmailVerificationRepository,
) {

    @Transactional
    fun signUp(command: SignUpUserCommand): UserDto {
        userValidator.validateSignUpUserCommand(command)

        return userRepository.save(command.toUser(passwordEncoder))
            .also { eventPublisher.publishEvent(UserSignedUpEvent(userId = it.id)) }
            .let { UserDto.from(it) }
    }

    @Transactional
    fun verifyUserEmail(userId: Long, token: UserEmailVerificationToken) {
        val emailVerification = userEmailVerificationRepository.findById(userId) ?: throw NotFoundUserEmailVerificationException(userId)

        emailVerification.verify(token, LocalDateTime.now())
    }

}
