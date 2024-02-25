package com.example.restapiboilerplate.domain.user.repository

import com.example.restapiboilerplate.SpringIntegrationTest
import com.example.restapiboilerplate.config.JpaConfig
import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.newUser
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.context.annotation.Import
import java.time.LocalDateTime

@Import(JpaConfig::class)
@DataJpaTest
class UserRepositoryTest(
    private val userRepository: UserRepository,
    private val entityManager: TestEntityManager,
) : SpringIntegrationTest({

    describe("save") {

        context("id가 0 인 User를 저장하면") {

            val actual = userRepository.save(newUser(id = 0L))

            it("id 는 0 이 아니어야 한다.") {
                actual.id shouldNotBe 0L
            }

            it("createdAt,modifiedAt 은 LocalDateTime.MIN 이 아니어야 한다") {
                actual.createdAt shouldNotBe LocalDateTime.MIN
                actual.modifiedAt shouldNotBe LocalDateTime.MIN
            }

        }

    }

    describe("findAll") {

        context("User 2명을 저장하고 있으면") {

            userRepository.save(newUser(name = "테스트1"))
            userRepository.save(newUser(name = "테스트2"))

            val actual = userRepository.findAll()

            it("User 목록의 크기는 2 여야 한다.") {
                actual shouldHaveSize 2
            }

            it("User 목록에 저장된 User 의 이름이 같아야 한다.") {
                actual.map { it.name } shouldContainExactlyInAnyOrder listOf("테스트1", "테스트2")
            }

        }

    }

    describe("findById") {

        val user = entityManager.persistAndFlush(newUser())

        context("이메일이 이미 저장된 경우") {

            val actual = userRepository.findById(user.id)

            it("null 을 반환하면 안된다.") {
                actual shouldNotBe null
            }

        }

        context("이메일이 저장되어 있지 않으면") {

            val actual = userRepository.findById(100000L)

            it("null 을 반환해야 한다.") {
                actual shouldBe null
            }

        }
    }

    describe("findByEmail") {

        val email = "test1234@test.com"
        userRepository.save(newUser(email = email))

        context("이메일이 이미 저장된 경우") {

            val actual = userRepository.findByEmail(Email(email))

            it("null 을 반환하면 안된다.") {
                actual shouldNotBe null
            }

        }

        context("이메일이 저장되어 있지 않으면") {

            val actual = userRepository.findByEmail(Email("test1235@test.com"))

            it("null 을 반환해야 한다.") {
                actual shouldBe null
            }

        }
    }

})
