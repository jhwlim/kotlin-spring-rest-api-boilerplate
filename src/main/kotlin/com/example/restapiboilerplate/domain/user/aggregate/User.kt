package com.example.restapiboilerplate.domain.user.aggregate

import com.example.restapiboilerplate.domain.user.value.Email
import com.example.restapiboilerplate.domain.user.value.Password
import com.example.restapiboilerplate.domain.user.value.UserStatus
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
    var email: Email,
    var name: String,
    var password: Password,
    var status: UserStatus,
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.MIN,
    @LastModifiedDate
    var modifiedAt: LocalDateTime = LocalDateTime.MIN,
)
