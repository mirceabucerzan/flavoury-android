package app.flavoury.signin.framework

import com.google.firebase.auth.FirebaseUser
import flavoury.libraries.core.domain.User

internal fun FirebaseUser.toDomainUser(): User {
    return User(uid, displayName ?: "", email ?: "", photoUrl)
}