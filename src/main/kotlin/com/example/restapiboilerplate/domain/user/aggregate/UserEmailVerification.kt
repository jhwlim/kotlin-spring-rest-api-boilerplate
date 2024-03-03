package com.example.restapiboilerplate.domain.user.aggregate

import com.example.restapiboilerplate.domain.user.exception.AlreadyVerifiedEmailException
import com.example.restapiboilerplate.domain.user.exception.UserEmailTokenExpiredException
import com.example.restapiboilerplate.domain.user.exception.UserEmailTokenNotMatchedException
import com.example.restapiboilerplate.domain.user.value.UserEmailVerificationToken
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "user_email_verifications")
class UserEmailVerification(
    var token: UserEmailVerificationToken,
    var expiredAt: LocalDateTime,
    var verifiedAt: LocalDateTime? = null,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", insertable = false, updatable = false, nullable = false)
    var id: Long = 0L,

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    var user: User,
) {

    fun verified(): Boolean = verifiedAt != null

    fun verify(token: UserEmailVerificationToken, current: LocalDateTime) {
        validateBeforeVerify(token, current)

        this.verifiedAt = current
        this.user.verifyEmail()
    }

    private fun validateBeforeVerify(token: UserEmailVerificationToken, current: LocalDateTime) {
        if (verified()) { throw AlreadyVerifiedEmailException() }
        if (this.token != token) { throw UserEmailTokenNotMatchedException() }
        if (expiredAt < current) { throw UserEmailTokenExpiredException() }
    }

}
