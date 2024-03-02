package com.example.restapiboilerplate.presentation.api

import com.example.restapiboilerplate.application.UserService
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import com.example.restapiboilerplate.presentation.api.dto.SignUpUserRequest
import com.example.restapiboilerplate.presentation.api.dto.UserResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/users")
class UserController(
    private val userService: UserService,
) {

    @PostMapping
    fun signUp(
        @RequestBody @Valid request: SignUpUserRequest,
    ): UserResponse {
        return UserResponse.from(
            userService.signUp(request.toSignUpUserCommand())
        )
    }

    @GetMapping("/{userId}/verifyEmail")
    fun verifyUserEmail(
        @PathVariable userId: Long,
        @RequestParam token: String,
    ) {
        userService.verifyUserEmail(userId, UserEmailVerificationToken(token))
    }

}
