package com.example.restapiboilerplate.domain.user.repository

import com.example.restapiboilerplate.SpringIntegrationTest
import com.example.restapiboilerplate.TestConstants
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_NAME
import com.example.restapiboilerplate.config.JpaConfig
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import com.example.restapiboilerplate.newUser
import com.example.restapiboilerplate.newUserEmailVerification
import io.kotest.matchers.reflection.beConst
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import

@Import(JpaConfig::class)
@DataJpaTest
class UserEmailVerificationRepositoryTest(
    private val userEmailVerificationRepository: UserEmailVerificationRepository,
    private val entityManager: TestEntityManager,
) : SpringIntegrationTest({

    describe("save") {

        context("userId 를 전달하면") {

            val user = entityManager.persist(newUser())
            val actual = userEmailVerificationRepository.save(newUserEmailVerification(user = user))

            it("id 는 userId 가 되어야 한다.") {
                actual.id shouldBe user.id
            }

        }

    }

    describe("findById") {

        context("userId 를 전달하면") {

            val user = entityManager.persistAndFlush(newUser())
            entityManager.persistAndFlush(newUserEmailVerification(user = user))

            entityManager.clear()

            val actual = userEmailVerificationRepository.findById(user.id)

            it("해당 userId 의 UserEmailVerification 을 조회해야 한다.") {
                actual.id shouldBe user.id
                actual.token shouldBe UserEmailVerificationToken(DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE)
            }

            it("해당 userId 의 User 를 조회해야 한다.") {
                actual.user.id shouldBe user.id
                actual.user.name shouldBe user.name
                actual.user.email shouldBe user.email
            }

        }

    }

})
