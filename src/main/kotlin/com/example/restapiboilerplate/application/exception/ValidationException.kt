package com.example.restapiboilerplate.application.exception

import com.example.restapiboilerplate.domain.common.exception.BaseException

class ValidationException(message: String) : BaseException(message)
