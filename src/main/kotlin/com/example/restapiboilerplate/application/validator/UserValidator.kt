package com.example.restapiboilerplate.application.validator

import com.example.restapiboilerplate.application.exception.ValidationException
import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import org.springframework.stereotype.Component

@Component
class UserValidator(
    private val userRepository: UserRepository,
) {

    fun validateSignUpUserCommand(command: SignUpUserCommand) {
        val savedUserWithEqualEmail = userRepository.findByEmail(command.email)
        if (savedUserWithEqualEmail != null) {
            throw ValidationException("이미 가입된 이메일 입니다.")
        }
    }

}
