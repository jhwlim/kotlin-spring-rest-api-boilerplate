package com.example.restapiboilerplate.domain.user.aggregate

import com.example.restapiboilerplate.TestConstants.DEFAULT_DATE_TIME
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE
import com.example.restapiboilerplate.domain.user.exception.AlreadyVerifiedEmailException
import com.example.restapiboilerplate.domain.user.exception.UserEmailTokenExpiredException
import com.example.restapiboilerplate.domain.user.exception.UserEmailTokenNotMatchedException
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureException
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import com.example.restapiboilerplate.domain.user.value.UserStatus.EMAIL_CHECK_COMPLETED
import com.example.restapiboilerplate.newUser
import com.example.restapiboilerplate.newUserEmailVerification
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class UserEmailVerificationTest : DescribeSpec({

    describe("verified") {

        context("증명 일자가 null 인 경우") {

            val user = newUser(id = 1L)
            val userEmailVerification = newUserEmailVerification(user = user)

            it("증명 여부는 false 여야 한다.") {
                userEmailVerification.verified() shouldBe false
            }

        }

        context("증명 일자가 있는 경우") {

            val user = newUser(id = 1L)
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

            val user = newUser(id = 1L)
            val userEmailVerification = newUserEmailVerification(user = user)

            userEmailVerification.verify(token, current)

            it("토큰의 증명 일자는 null 이 아니어야 한다.") {
                userEmailVerification.verifiedAt shouldNotBe null
            }

            it("User 는 이메일 인증 상태여야 한다.") {
                userEmailVerification.user.status shouldBe EMAIL_CHECK_COMPLETED
            }

        }

        context("이메일 인증에 실패하는 경우") {

            forAll(
                row("이미 인증된 이메일인 경우", AlreadyVerifiedEmailException(), newUserEmailVerification(user = newUser(id = 1L), verifiedAt = DEFAULT_DATE_TIME)),
                row("토큰이 일치하지 않는 경우", UserEmailTokenNotMatchedException(), newUserEmailVerification(user = newUser(id = 1L), token = "${DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE}_1")),
                row("토큰이 만료된 경우", UserEmailTokenExpiredException(), newUserEmailVerification(user = newUser(id = 1L), expiredAt = DEFAULT_DATE_TIME.minusNanos(1L)))

            ) { context, expectedException, userEmailVerification,  ->

                context(context) {

                    it("예외가 발생해야 한다.") {
                        val actualException = shouldThrow<VerifyUserEmailFailureException> {
                            userEmailVerification.verify(token, current)
                        }
                        actualException shouldBe expectedException
                    }

                }

            }

        }

    }

})
