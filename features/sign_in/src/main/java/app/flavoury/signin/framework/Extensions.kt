package app.flavoury.signin.framework

import app.flavoury.signin.domain.User
import com.google.firebase.auth.FirebaseUser

fun FirebaseUser.toDomainUser(): User {
    return User(uid, displayName ?: "", email ?: "", photoUrl)
}