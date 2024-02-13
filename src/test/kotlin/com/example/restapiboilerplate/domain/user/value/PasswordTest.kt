package com.example.restapiboilerplate.domain.user.value

import com.example.restapiboilerplate.shouldThrowIllegalArgumentExceptionWithMessage
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row

class PasswordTest : DescribeSpec({

    describe("init") {

        context("숫자가 주어진 경우") {

            it("예외가 발생하지 않아야 한다") {

                shouldNotThrowAny {
                    Password("1234567890")
                }

            }

        }

        context("숫자가 아닌 문자가 주어진 경우") {

            forAll(
                row("영문이 포함된 경우", "1234a"),
                row("한글이 포함된 경우", "1234가"),
                row("특수문자가 포함된 경우", "1234@"),
            ) { description, value ->

                it("$description - 예외가 발생해야 한다.") {

                    shouldThrowIllegalArgumentExceptionWithMessage("비밀번호 형식이 올바르지 않습니다.") {
                        Password(value)
                    }

                }

            }

        }

    }

})
