package com.example.restapiboilerplate.domain.user.aggregate

import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureException
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureReasonType.EXPIRED_TOKEN
import com.example.restapiboilerplate.domain.user.exception.VerifyUserEmailFailureReasonType.NOT_MATCHED_TOKEN
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
        checkToken(token, current)

        this.verifiedAt = current
    }

    private fun checkToken(token: UserEmailVerificationToken, current: LocalDateTime) {
        if (this.token != token) { throw VerifyUserEmailFailureException(NOT_MATCHED_TOKEN) }
        if (this.expiredAt < current) { throw VerifyUserEmailFailureException(EXPIRED_TOKEN) }
    }

}
