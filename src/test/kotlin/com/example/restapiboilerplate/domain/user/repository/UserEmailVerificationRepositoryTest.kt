package com.example.restapiboilerplate.domain.user.repository

import com.example.restapiboilerplate.SpringIntegrationTest
import com.example.restapiboilerplate.TestConstants.DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE
import com.example.restapiboilerplate.config.JpaConfig
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import com.example.restapiboilerplate.newUser
import com.example.restapiboilerplate.newUserEmailVerification
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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

        val user = entityManager.persist(newUser())

        context("저장되어 있지 않은 token 을 전달하면") {

            val actual = userEmailVerificationRepository.save(newUserEmailVerification(user = user))

            it("id 는 userId 가 되어야 한다.") {
                actual.id shouldBe user.id
            }

        }

    }

    describe("findById") {

        val user = entityManager.persist(newUser())

        context("전달하는 userId 의 UserEmailVerification 가 존재하는 경우") {

            entityManager.persistAndFlush(newUserEmailVerification(user = user))
            entityManager.clear()

            val actual = userEmailVerificationRepository.findById(user.id)

            it("해당 userId 의 UserEmailVerification 을 조회해야 한다.") {
                actual shouldNotBe null
                actual?.id shouldBe user.id
                actual?.token shouldBe UserEmailVerificationToken(DEFAULT_USER_EMAIL_VERIFICATION_TOKEN_VALUE)
            }

            it("해당 userId 의 User 를 조회해야 한다.") {
                actual?.user?.id shouldBe user.id
                actual?.user?.name shouldBe user.name
                actual?.user?.email shouldBe user.email
            }

        }

        context("전달하는 userId 의 UserEmailVerification 가 존재하지 않는 경우") {

            val actual = userEmailVerificationRepository.findById(10000L)

            it("null을 반환해야 한다.") {
                actual shouldBe null
            }

        }

    }

})
