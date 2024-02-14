package com.example.restapiboilerplate.application

import com.example.restapiboilerplate.application.validator.UserValidator
import com.example.restapiboilerplate.domain.user.event.UserSignedUpEvent
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import com.example.restapiboilerplate.newSignUpUserCommand
import com.example.restapiboilerplate.newUser
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk
import org.springframework.context.ApplicationEventPublisher

class UserServiceTest : DescribeSpec({

    val userValidator = mockk<UserValidator>()
    val userRepository = mockk<UserRepository>()
    val eventPublisher = mockk<ApplicationEventPublisher>()
    val userService = UserService(
        userValidator,
        userRepository,
        eventPublisher,
    )

    describe("signUp") {

        context("User 등록에 문제가 없는 경우") {

            val command = newSignUpUserCommand()
            val savedUser = newUser(id = 1L)
            every { userValidator.validateSignUpUserCommand(command) } answers {}
            every { userRepository.save(any()) } answers { savedUser }
            every { eventPublisher.publishEvent(UserSignedUpEvent(userId = 1L)) } answers {}

            it("예외가 발생하지 않아야 한다.") {
                shouldNotThrowAny { userService.signUp(command) }
            }

        }

    }

})
