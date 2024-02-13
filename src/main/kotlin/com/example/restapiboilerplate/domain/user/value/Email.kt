package com.example.restapiboilerplate.domain.user.value

import java.util.regex.Pattern

@JvmInline
value class Email(
    private val value: String,
) {

    init {
        val matcher = PATTERN.matcher(value)

        require(matcher.matches()) { "이메일 형식이 올바르지 않습니다." }
    }

    companion object {
        private const val REGEX = "\\w+@\\w+\\.\\w+(\\.\\w+)?"
        private val PATTERN = Pattern.compile(REGEX)
    }

}
