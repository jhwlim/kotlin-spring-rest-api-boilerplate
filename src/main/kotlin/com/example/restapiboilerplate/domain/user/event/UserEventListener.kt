package com.example.restapiboilerplate.domain.user.event

import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class UserEventListener {

    @EventListener
    fun handleUserSignedUpEvent(event: UserSignedUpEvent) {
        TODO()
    }

}
