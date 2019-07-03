package app.flavoury.signin

import android.content.Context
import flavoury.libraries.core.domain.User

/**
 * The Sign In module's interface with the outside world.
 */
interface SignInInfoSource {
    fun wasSignInSkipped(): Boolean

    fun getSignedInUser(): User?
}

class RealSignInInfoSource(context: Context) : SignInInfoSource {
    private val repository = provideSignInRepository(context)

    override fun wasSignInSkipped(): Boolean = repository.wasSignInSkipped()

    override fun getSignedInUser(): User? = repository.getSignedInUser()
}