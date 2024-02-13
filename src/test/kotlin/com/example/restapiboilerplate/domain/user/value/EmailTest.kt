package com.example.restapiboilerplate.domain.user.value

import com.example.restapiboilerplate.shouldThrowIllegalArgumentExceptionWithMessage
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.data.forAll
import io.kotest.data.row

class EmailTest : DescribeSpec({

    describe("init") {

        context("올바른 형식의 문자열이 주어진 경우") {

            forAll(
                row("영문숫자@영문숫자.영문숫자", "test1234@test1234.test1234"),
                row("영문숫자@영문숫자.영문숫자.영문숫자", "test1234@test1234.test1234.test1234"),
            ) { description, value ->

                it("$description - 예외가 발생하지 않아야 한다.") {

                    shouldNotThrowAny {
                        Email(value)
                    }

                }

            }

        }

        context("문자열 형식이 올바르지 않은 경우") {

            forAll(
                row("영문숫자@영문숫자", "test1234@test1234"),
                row("영문숫자@영문숫자.영문숫자.영문숫자.영문숫자", "test1234@test1234.test1234.test1234,test1234"),
                row("한글 포함", "테스트1234@test1234.test1234"),
                row("특수문자 포함", "test1234!@test1234.test1234"),
            ) { description, value ->

                it("$description - 예외가 발생해야 한다.") {

                    shouldThrowIllegalArgumentExceptionWithMessage("이메일 형식이 올바르지 않습니다.") {
                        Email(value)
                    }

                }

            }

        }

    }

})
