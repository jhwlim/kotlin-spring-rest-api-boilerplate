package com.example.restapiboilerplate.domain.user.event

import com.example.restapiboilerplate.domain.user.component.UserEmailVerificationSender
import com.example.restapiboilerplate.domain.user.exception.NotFoundUserException
import com.example.restapiboilerplate.domain.user.repository.UserEmailVerificationRepository
import com.example.restapiboilerplate.domain.user.repository.UserRepository
import com.example.restapiboilerplate.newUser
import com.example.restapiboilerplate.newUserEmailVerification
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class UserEventListenerTest : DescribeSpec({

    val userRepository = mockk<UserRepository>()
    val userEmailVerificationRepository = mockk<UserEmailVerificationRepository>()
    val userEmailVerificationSender = mockk<UserEmailVerificationSender>()
    val userEventListener = UserEventListener(
        userRepository,
        userEmailVerificationRepository,
        userEmailVerificationSender,
    )

    describe("handleUserSignedUpEvent") {

        val userId = 1L

        context("User 이메일 증명 발송에 문제가 없는 경우") {

            val user = newUser(id = userId)
            val newUserEmailVerification = newUserEmailVerification(user = user)
            every { userRepository.findById(userId) } answers { user }
            every { userEmailVerificationRepository.save(any()) } answers { newUserEmailVerification }
            every { userEmailVerificationSender.sendUserEmailVerification(newUserEmailVerification) } answers {}

            it("예외가 발생하지 않아야 한다.") {
                shouldNotThrowAny { userEventListener.handleUserSignedUpEvent(UserSignedUpEvent(userId)) }
            }

        }

        context("User 정보를 찾을 수 없는 경우") {

            every { userRepository.findById(userId) } answers { null }

            it("예외가 발생해야 한다.") {
                val actualException = shouldThrow<NotFoundUserException> {
                    userEventListener.handleUserSignedUpEvent(UserSignedUpEvent(userId))
                }
                actualException shouldBe NotFoundUserException(userId)
            }

        }

    }

})
