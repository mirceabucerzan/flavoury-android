package app.flavoury.signin.domain

import android.net.Uri

/**
 * Business model for a Flavoury user.
 */
data class User(
    val uid: String,
    val displayName: String,
    val email: String,
    val photoUrl: Uri?
)