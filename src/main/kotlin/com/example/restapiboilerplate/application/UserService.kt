package com.example.restapiboilerplate.application

import com.example.restapiboilerplate.application.dto.UserDto
import com.example.restapiboilerplate.application.validator.UserValidator
import com.example.restapiboilerplate.domain.user.event.UserSignedUpEvent
import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userValidator: UserValidator,
    private val userRepository: UserRepository,
    private val eventPublisher: ApplicationEventPublisher,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun signUp(command: SignUpUserCommand): UserDto {
        userValidator.validateSignUpUserCommand(command)

        return userRepository.save(command.toUser(passwordEncoder))
            .also { eventPublisher.publishEvent(UserSignedUpEvent(userId = it.id)) }
            .let { UserDto.from(it) }
    }

}
