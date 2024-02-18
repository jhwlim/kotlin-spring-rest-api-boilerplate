package com.example.restapiboilerplate.domain.user.value

import com.example.restapiboilerplate.shouldThrowIllegalArgumentExceptionWithMessage
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe

class PasswordTest : DescribeSpec({

    describe("toString") {

        context("문자열이 주어진 경우") {

            val actual = Password("123456")

            it("'*' 가 주어진 문자열의 길이만큼 반환해야 한다.") {
                actual.toString() shouldBe "******"
            }

        }

    }

})
