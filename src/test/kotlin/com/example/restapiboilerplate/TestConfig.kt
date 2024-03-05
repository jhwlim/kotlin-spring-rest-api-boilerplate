package com.example.restapiboilerplate

import io.mockk.spyk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary

@TestConfiguration
class TestConfig {

    @Primary
    @Bean
    fun eventPublisher(): ApplicationEventPublisher {
        return spyk()
    }

}
