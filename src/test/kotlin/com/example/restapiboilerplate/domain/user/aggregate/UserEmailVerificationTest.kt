package com.example.restapiboilerplate.domain.user.aggregate

import com.example.restapiboilerplate.TestConstants
import com.example.restapiboilerplate.TestConstants.DEFAULT_DATE_TIME
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureException
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureType.EXPIRED_TOKEN
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureType.NOT_MATCHED_TOKEN
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import com.example.restapiboilerplate.newUser
import com.example.restapiboilerplate.newUserEmailVerification
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class UserEmailVerificationTest : DescribeSpec({

    val user = newUser(id = 1L)

    describe("verified") {

        context("증명 일자가 null 인 경우") {

            val userEmailVerification = newUserEmailVerification(user = user)

            it("증명 여부는 false 여야 한다.") {
                userEmailVerification.verified() shouldBe false
            }

        }

        context("증명 일자가 있는 경우") {

            val userEmailVerification = newUserEmailVerification(user = user, verifiedAt = DEFAULT_DATE_TIME.plusSeconds(1L))

            it("증명 여부는 true 여야 한다.") {
                userEmailVerification.verified() shouldBe true
            }

        }

    }

    describe("verify") {

        val token = UserEmailVerificationToken(DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE)
        val current = DEFAULT_DATE_TIME

        context("토큰 증명에 문제가 없는 경우") {

            val userEmailVerification = newUserEmailVerification(user = user)

            userEmailVerification.verify(token, current)

            it("토큰의 증명 일자는 null 이 아니어야 한다.") {
                userEmailVerification.verifiedAt shouldNotBe null
            }

        }

        context("토큰이 일치하지 않는 경우") {

            val userEmailVerification = newUserEmailVerification(user = user)
            val differentToken = UserEmailVerificationToken("${DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE}_1")

            val actual = shouldThrow<VerifyUserEmailFailureException> {
                userEmailVerification.verify(differentToken, current)
            }

            it("예외가 발생해야 한다.") {
                actual shouldBe VerifyUserEmailFailureException(NOT_MATCHED_TOKEN)
            }

        }

        context("토큰이 만료된 경우") {

            val userEmailVerification = newUserEmailVerification(user = user)
            val afterExpiredAt = DEFAULT_DATE_TIME.plusNanos(1L)

            val actual = shouldThrow<VerifyUserEmailFailureException> {
                userEmailVerification.verify(token, afterExpiredAt)
            }

            it("예외가 발생해야 한다.") {
                actual shouldBe VerifyUserEmailFailureException(EXPIRED_TOKEN)
            }

        }

    }

})
