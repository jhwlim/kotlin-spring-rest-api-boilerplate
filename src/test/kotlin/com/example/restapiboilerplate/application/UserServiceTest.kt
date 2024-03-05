package com.example.restapiboilerplate.application

import com.example.restapiboilerplate.TestConstants.DEFAULT_DATE_TIME
import com.example.restapiboilerplate.application.validator.UserValidator
import com.example.restapiboilerplate.domain.user.exception.*
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
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

class UserServiceTest : DescribeSpec({

    val userValidator = mockk<UserValidator>()
    val userRepository = mockk<UserRepository>()
    val passwordEncoder = mockk<PasswordEncoder>()
    val userEmailVerificationRepository = mockk<UserEmailVerificationRepository>()
    val userService = UserService(
        userValidator,
        userRepository,
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

            it("예외가 발생하지 않아야 한다.") {
                shouldNotThrowAny { userService.signUp(command) }
            }

        }

    }

    describe("verifyUserEmail") {

        val userId = 1L
        val user = newUser(id = userId)
        val token = UserEmailVerificationToken("test")

        mockkStatic(LocalDateTime::class)

        context("token 인증에 문제가 없는 경우") {

            val savedUserEmailVerification = newUserEmailVerification(user = user, token = "test")

            every { userEmailVerificationRepository.findById(userId) } answers { savedUserEmailVerification }
            every { LocalDateTime.now() } answers { DEFAULT_DATE_TIME }

            it("예외가 발생하지 않아야 한다.") {
                shouldNotThrowAny { userService.verifyUserEmail(userId, token) }
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
                row("이미 인증된 이메일인 경우", AlreadyVerifiedEmailException(), token, DEFAULT_DATE_TIME, DEFAULT_DATE_TIME.minusNanos(1)),
                row("토큰이 일치하지 않는 경우", UserEmailTokenNotMatchedException(), UserEmailVerificationToken("test12"), DEFAULT_DATE_TIME, null),
                row("토큰이 만료된 경우", UserEmailTokenExpiredException(), token, DEFAULT_DATE_TIME.plusNanos(1), null),
            ) { context, expectedException, token, current, verifiedAt ->

                val savedUserEmailVerification = newUserEmailVerification(user = user, token = "test", verifiedAt = verifiedAt)

                every { userEmailVerificationRepository.findById(userId) } answers { savedUserEmailVerification }
                every { LocalDateTime.now() } answers { current }

                context(context) {

                    it("예외가 발생해야 한다.") {
                        val actualException = shouldThrow<VerifyUserEmailFailureException> {
                            userService.verifyUserEmail(userId, token)
                        }
                        actualException shouldBe expectedException
                    }

                }
            }

        }

    }

})
