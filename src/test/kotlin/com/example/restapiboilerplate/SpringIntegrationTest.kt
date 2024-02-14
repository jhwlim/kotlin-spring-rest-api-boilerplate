package com.example.restapiboilerplate

import io.kotest.core.extensions.Extension
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.extensions.spring.SpringExtension
import io.kotest.extensions.spring.SpringTestExtension
import io.kotest.extensions.spring.SpringTestLifecycleMode
import org.springframework.stereotype.Component
import org.springframework.test.context.ContextConfiguration

abstract class SpringIntegrationTest(
    body: DescribeSpec.() -> Unit
) : DescribeSpec(body) {

    override fun extensions(): List<Extension> = listOf(SpringTestExtension(SpringTestLifecycleMode.Root))

}
