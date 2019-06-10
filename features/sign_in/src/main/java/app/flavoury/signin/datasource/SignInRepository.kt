package app.flavoury.signin.datasource

import com.mirceabucerzan.core.domain.User

/**
 * Repository that handles sign in data operations.
 */
class SignInRepository<T>(
    private val googleSignInDataSource: GoogleSignInDataSource<T>,
    private val localDataSource: LocalDataSource
) {

    fun getSignInInitData(authProvider: AuthProvider): T {
        return when (authProvider) {
            is AuthProvider.Google -> googleSignInDataSource.getSignInInitData()
        }
    }

    suspend fun signIn(authProvider: AuthProvider, data: T): User? {
        return when (authProvider) {
            is AuthProvider.Google -> {
                val user: User? = googleSignInDataSource.signIn(data)
                if (user != null) {
                    // Signed in, clear the cached sign in skipped value
                    localDataSource.signInSkipped = false
                }
                user
            }
        }
    }

    fun getSignedInUser(authProvider: AuthProvider = AuthProvider.Google): User? {
        return when (authProvider) {
            is AuthProvider.Google -> googleSignInDataSource.getSignedInUser()
        }
    }

    fun skipSignIn() {
        localDataSource.signInSkipped = true
    }

    fun isSignInSkipped(): Boolean = localDataSource.signInSkipped

}

/**
 * Abstract data source for sign in data.
 */
interface GoogleSignInDataSource<T> {
    fun getSignInInitData(): T

    suspend fun signIn(data: T): User?

    fun getSignedInUser(): User?
}

/**
 * Abstract data source for locally cached data.
 */
interface LocalDataSource {
    var signInSkipped: Boolean
}

/**
 * Supported sign in auth providers.
 */
sealed class AuthProvider {
    object Google : AuthProvider()
}