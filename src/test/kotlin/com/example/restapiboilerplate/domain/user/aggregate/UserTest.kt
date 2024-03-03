package com.example.restapiboilerplate.domain.user.aggregate

import com.example.restapiboilerplate.domain.user.value.UserStatus.EMAIL_CHECK_COMPLETED
import com.example.restapiboilerplate.newUser
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class UserTest : DescribeSpec({

    describe("verifyEmail") {

        context("이메일 인증에 문제가 없는 경우") {

            val user = newUser(id = 1L)

            user.verifyEmail()

            it("status 는 이메일 인증 상태가 되어야 한다.") {
                user.status shouldBe EMAIL_CHECK_COMPLETED
            }

        }

        context("이메일이 이미 인증된 상태인 경우") {

            val user = newUser(id = 1L, status = EMAIL_CHECK_COMPLETED)

            it("예외가 발생해야 한다.") {
                shouldThrowWithMessage <IllegalStateException>("User Email is already verified") {
                    user.verifyEmail()
                }
            }

        }

    }

})
