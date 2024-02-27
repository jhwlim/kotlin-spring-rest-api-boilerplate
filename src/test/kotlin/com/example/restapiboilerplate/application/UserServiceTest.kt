package com.example.restapiboilerplate.application

import com.example.restapiboilerplate.TestConstants.DEFAULT_DATE_TIME
import com.example.restapiboilerplate.application.dto.UserEmailVerificationResultDto
import com.example.restapiboilerplate.application.validator.UserValidator
import com.example.restapiboilerplate.domain.user.event.UserSignedUpEvent
import com.example.restapiboilerplate.domain.user.exception.NotFoundUserEmailVerificationException
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureType
import com.example.restapiboilerplate.domain.user.repository.UserEmailVerificationRepository
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import com.example.restapiboilerplate.newSignUpUserCommand
import com.example.restapiboilerplate.newUser
import com.example.restapiboilerplate.newUserEmailVerification
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import org.springframework.context.ApplicationEventPublisher
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

class UserServiceTest : DescribeSpec({

    val userValidator = mockk<UserValidator>()
    val userRepository = mockk<UserRepository>()
    val eventPublisher = mockk<ApplicationEventPublisher>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val userEmailVerificationRepository = mockk<UserEmailVerificationRepository>()
    val userService = UserService(
        userValidator,
        userRepository,
        eventPublisher,
        passwordEncoder,
        userEmailVerificationRepository,
    )

    describe("signUp") {

        val userId = 1L
        val command = newSignUpUserCommand()
        val savedUser = newUser(id = userId, password = "abcd")

        context("User 등록에 문제가 없는 경우") {

            every { userValidator.validateSignUpUserCommand(command) } answers {}
            every { passwordEncoder.encode(command.rawPassword) } answers { "abcd" }
            every { userRepository.save(any()) } answers { savedUser }
            every { eventPublisher.publishEvent(UserSignedUpEvent(userId = userId)) } answers {}

            it("예외가 발생하지 않아야 한다.") {
                shouldNotThrowAny { userService.signUp(command) }
            }

        }

    }

    describe("verifyUserEmail") {

        val userId = 1L
        val user = newUser(id = userId)
        val token = UserEmailVerificationToken("test")
        val savedUserEmailVerification = newUserEmailVerification(user = user, token = "test")

        mockkStatic(LocalDateTime::class)

        context("token 인증에 문제가 없는 경우") {

            every { userEmailVerificationRepository.findById(userId) } answers { savedUserEmailVerification }
            every { LocalDateTime.now() } answers { DEFAULT_DATE_TIME }

            val actual = userService.verifyUserEmail(userId, token)

            it("인증 성공 결과를 반환해야 한다.") {
                actual shouldBe UserEmailVerificationResultDto.success()
            }

        }

        context("User 에 등록된 token 을 찾을 수 없는 경우") {

            val notSavedUserId = 10000L
            every { userEmailVerificationRepository.findById(notSavedUserId) } answers { null }

            it("예외가 발생해야 한다.") {
                val actualException = shouldThrow<NotFoundUserEmailVerificationException> {
                    userService.verifyUserEmail(notSavedUserId, token)
                }
                actualException shouldBe NotFoundUserEmailVerificationException(notSavedUserId)
            }

        }

        context("token 인증에 실패한 경우") {

            forAll(
                row(VerifyUserEmailFailureType.NOT_MATCHED_TOKEN, UserEmailVerificationToken("test12"), DEFAULT_DATE_TIME),
                row(VerifyUserEmailFailureType.EXPIRED_TOKEN, token, DEFAULT_DATE_TIME.plusNanos(1L)),
            ) { expectedFailureType, token, current ->

                every { userEmailVerificationRepository.findById(userId) } answers { savedUserEmailVerification }
                every { LocalDateTime.now() } answers { current }

                context("인증 실패 타입 : ${expectedFailureType.name}") {

                    val actual = userService.verifyUserEmail(userId, token)

                    it("인증 실패 결과를 반환해야 한다.") {
                        actual shouldBe UserEmailVerificationResultDto(false, expectedFailureType)
                    }

                }
            }

        }

    }

})
