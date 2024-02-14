package com.example.restapiboilerplate.domain.user.repository

import com.example.restapiboilerplate.SpringIntegrationTest
import com.example.restapiboilerplate.newUser
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class UserRepositoryTest(
    private val userRepository: UserRepository,
) : SpringIntegrationTest({

    describe("save") {

        context("id가 0 인 User를 저장하면") {

            val actual = userRepository.save(newUser(id = 0L))

            it("id 는 0 이 아니아어야 한다.") {
                actual.id shouldNotBe 0L
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

})
