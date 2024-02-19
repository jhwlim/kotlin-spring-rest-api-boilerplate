package com.example.restapiboilerplate.domain.user.aggregate

import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_email_verifications")
class UserEmailVerification(
    var token: UserEmailVerificationToken,
    var verified: Boolean = false,
    var publishedAt: LocalDateTime,
    var verifiedAt: LocalDateTime? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    var id: Long = 0L,

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    var user: User,
)
