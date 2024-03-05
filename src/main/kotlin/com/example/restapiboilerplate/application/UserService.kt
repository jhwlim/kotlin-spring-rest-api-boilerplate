package com.example.restapiboilerplate.application

import com.example.restapiboilerplate.application.dto.UserDto
import com.example.restapiboilerplate.application.validator.UserValidator
import com.example.restapiboilerplate.domain.user.exception.NotFoundUserEmailVerificationException
import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.repository.UserEmailVerificationRepository
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val userValidator: UserValidator,
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val userEmailVerificationRepository: UserEmailVerificationRepository,
) {

    @Transactional
    fun signUp(command: SignUpUserCommand): UserDto {
        userValidator.validateSignUpUserCommand(command)

        return UserDto.from(
            userRepository.save(command.toUser(passwordEncoder))
        )
    }

    @Transactional
    fun verifyUserEmail(userId: Long, token: UserEmailVerificationToken) {
        val emailVerification = userEmailVerificationRepository.findById(userId) ?: throw NotFoundUserEmailVerificationException(userId)

        emailVerification.verify(token, LocalDateTime.now())
    }

}
