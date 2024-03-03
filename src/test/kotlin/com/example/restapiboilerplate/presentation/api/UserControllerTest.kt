package com.example.restapiboilerplate.presentation.api

import com.example.restapiboilerplate.*
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE
import com.example.restapiboilerplate.application.UserService
import com.example.restapiboilerplate.application.exception.ValidationException
import com.example.restapiboilerplate.config.SecurityConfig
import com.example.restapiboilerplate.domain.user.exception.AlreadyVerifiedEmailException
import com.example.restapiboilerplate.domain.user.exception.NotFoundUserEmailVerificationException
import com.example.restapiboilerplate.domain.user.exception.UserEmailTokenExpiredException
import com.example.restapiboilerplate.domain.user.exception.UserEmailTokenNotMatchedException
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import com.example.restapiboilerplate.presentation.advice.ErrorResponse
import com.example.restapiboilerplate.presentation.api.dto.UserResponse
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import io.mockk.every
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@WebMvcTest(UserController::class)
@Import(SecurityConfig::class)
class UserControllerTest(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    @MockkBean
    private val userService: UserService,
) : SpringIntegrationTest({

    val errorMessage = "테스트 에러 메시지"

    describe("회원가입") {

        val signUp = { request: SignUpUserRequestWithNull ->
            mockMvc.post("/v1/users") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(request)
            }.andDo { print() }
        }

        context("회원가입이 가능한 경우") {

            every { userService.signUp(newSignUpUserCommand()) } answers { newUserDto() }

            val actual = signUp(newSignUpUserRequest())

            it("status 는 200 을 반환해야 한다.") {
                actual.andExpect {
                    status { isOk() }
                }
            }

            it("가입한 User 정보를 반환해야 한다.") {
                val actualResponse = objectMapper.readResponse(actual.andReturn(), UserResponse::class)

                actualResponse shouldBe newUserResponse()
            }

        }

        context("request 가 유효하지 않은 경우") {

            forAll(
                row("name 이 null 인 경우", newSignUpUserRequest(name = null), "name 이 null 입니다."),
                row("name 이 비어있는 경우", newSignUpUserRequest(name = ""), "name 은 비어있을 수 없습니다."),
                row("email 이 null 인 경우", newSignUpUserRequest(email = null), "email 이 null 입니다."),
                row("email 이 비어있는 경우", newSignUpUserRequest(email = ""), "email 은 비어있을 수 없습니다."),
                row("email 형식이 잘못된 경우", newSignUpUserRequest(email = "test1234"), "email 형식이 올바르지 않습니다."),
                row("password 가 null 인 경우", newSignUpUserRequest(password = null), "password 이 null 입니다."),
                row("password 가 비어있는 경우", newSignUpUserRequest(password = ""), "password 는 비어있을 수 없습니다."),
            ) { description, request, errorMessage ->

                context(description) {

                    val actual = signUp(request)

                    it("status 는 400 을 반환해야 한다.") {
                        actual.andExpect {
                            status { isBadRequest() }
                        }
                    }

                    it("errorCode 는 ERR-901 을 반환해야 한다.") {
                        val actualResponse = objectMapper.readErrorResponse(actual.andReturn())

                        actualResponse shouldBe ErrorResponse(errorCode = "ERR-901", message = errorMessage)
                    }

                }

            }

        }

        context("Validation 에러가 발생한 경우") {

            every { userService.signUp(newSignUpUserCommand()) } throws ValidationException(errorMessage)

            val actual = signUp(newSignUpUserRequest())

            it("status 는 400 을 반환해야 한다.") {
                actual.andExpect {
                    status { isBadRequest() }
                }
            }

            it("errorCode 는 ERR-901 을 반환해야 한다.") {
                val actualResponse = objectMapper.readErrorResponse(actual.andReturn())

                actualResponse shouldBe ErrorResponse(errorCode = "ERR-901", message = errorMessage)
            }

        }

        context("기타 에러가 발생한 경우") {

            every { userService.signUp(newSignUpUserCommand()) } throws Exception(errorMessage)

            val actual = signUp(newSignUpUserRequest())

            it("status 는 500 을 반환해야 한다.") {
                actual.andExpect {
                    status { isInternalServerError() }
                }
            }

            it("errorCode 는 ERR-999 을 반환해야 한다.") {
                val actualResponse = objectMapper.readErrorResponse(actual.andReturn())

                actualResponse shouldBe ErrorResponse(errorCode = "ERR-999", message = errorMessage)
            }

        }

    }

    describe("이메일 인증") {

        val verifyEmail = { userId: Long, token: String ->
            mockMvc.get("/v1/users/$userId/verifyEmail?token=$token") {
                contentType = MediaType.APPLICATION_JSON
            }.andDo { print() }
        }
        val userId = 1L
        val token = DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE

        context("이메일 인증이 가능한 경우") {

            every { userService.verifyUserEmail(userId, UserEmailVerificationToken(token)) } answers {}

            val actual = verifyEmail(userId, token)

            it("status 는 200 을 반환해야 한다.") {
                actual.andExpect {
                    status { isOk() }
                }
            }

            it("body 는 비어있어야 한다.") {
                val actualReturn = actual.andReturn()
                actualReturn.response.contentAsString.isBlank() shouldBe true
            }

        }

        context("UserService 에서 예외가 발생한 경우") {

            forAll(
                row(AlreadyVerifiedEmailException(), 400, "ERR-121"),
                row(UserEmailTokenNotMatchedException(), 400, "ERR-122"),
                row(UserEmailTokenExpiredException(), 400, "ERR-123"),
                row(NotFoundUserEmailVerificationException(1L), 404, "ERR-102"),
                row(Exception("테스트 메시지"), 500, "ERR-999")
            ) { serviceException, expectedStatus, expectedErrorCode ->

                every { userService.verifyUserEmail(userId, UserEmailVerificationToken(token)) } throws serviceException

                val actual = verifyEmail(userId, token)

                it("status 는 $expectedStatus 을 반환해야 한다.") {
                    actual.andExpect {
                        status { isEqualTo(expectedStatus) }
                    }
                }

                it("errorCode 는 $expectedErrorCode 을 반환해야 한다.") {
                    val actualResponse = objectMapper.readErrorResponse(actual.andReturn())

                    actualResponse shouldBe ErrorResponse(
                        errorCode = expectedErrorCode,
                        message = serviceException.message ?: ""
                    )
                }

            }

        }

    }

})
