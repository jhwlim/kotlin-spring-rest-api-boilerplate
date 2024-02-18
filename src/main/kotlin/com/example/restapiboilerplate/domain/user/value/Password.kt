package com.example.restapiboilerplate.domain.user.value

@JvmInline
value class Password(
    private val value: String
) {

    override fun toString(): String {
        return CHAR.repeat(value.length)
    }

    companion object {

        private const val CHAR = "*"

    }

}
