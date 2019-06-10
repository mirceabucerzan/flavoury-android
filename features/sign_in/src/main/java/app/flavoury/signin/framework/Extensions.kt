package app.flavoury.signin.framework

import com.google.firebase.auth.FirebaseUser
import com.mirceabucerzan.core.domain.User

fun FirebaseUser.toDomainUser(): User {
    return User(uid, displayName ?: "", email ?: "", photoUrl)
}