package com.example.restapiboilerplate.application.validator

import com.example.restapiboilerplate.application.exception.ValidationException
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.newSignUpUserCommand
import com.example.restapiboilerplate.newUser
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.mockk

class UserValidatorTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val userValidator = UserValidator(
        userRepository
    )

    describe("validateSignUpUserCommand") {

        context("User 등록에 문제가 없는 경우") {

            val email = "test1234@test.com"
            val command = newSignUpUserCommand(email = email)
            every { userRepository.findByEmail(Email(email)) } answers { null }

            it("예외가 발생하지 않아야 한다.") {
                shouldNotThrowAny { userValidator.validateSignUpUserCommand(command) }
            }

        }

        context("이메일이 이미 존재하는 경우") {

            val email = "test1234@test.com"
            val command = newSignUpUserCommand(email = email)
            every { userRepository.findByEmail(Email(email)) } answers { newUser(email = email) }

            it("예외가 발생해야 한다.") {
                shouldThrowWithMessage<ValidationException>("이미 가입된 이메일 입니다.") { userValidator.validateSignUpUserCommand(command) }
            }

        }

    }

})
