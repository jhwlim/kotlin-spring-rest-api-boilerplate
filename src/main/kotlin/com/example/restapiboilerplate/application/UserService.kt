package com.example.restapiboilerplate.application

import com.example.restapiboilerplate.application.dto.UserDto
import com.example.restapiboilerplate.domain.user.model.SignUpUserCommand
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    @Transactional
    fun signUp(command: SignUpUserCommand): UserDto {
        return UserDto.from(
            userRepository.save(command.toUser())
        )
    }

}
