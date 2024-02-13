package com.example.restapiboilerplate.domain.user.value

import java.util.regex.Pattern

@JvmInline
value class Password(
    private val value: String
) {

    init {
        val matcher = PATTERN.matcher(value)

        require(matcher.matches()) { "비밀번호 형식이 올바르지 않습니다." }
    }

    companion object {
        private const val REGEX = "^[0-9]*\$"
        private val PATTERN = Pattern.compile(REGEX)
    }

}
