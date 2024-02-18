package com.example.restapiboilerplate.presentation.api

import com.example.restapiboilerplate.application.UserService
import com.example.restapiboilerplate.presentation.api.dto.SignUpUserRequest
import com.example.restapiboilerplate.presentation.api.dto.UserResponse
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

}
