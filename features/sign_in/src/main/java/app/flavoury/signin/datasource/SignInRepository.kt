package app.flavoury.signin.datasource

import flavoury.libraries.core.domain.User

/**
 * Repository that handles sign in data operations.
 */
internal class SignInRepository<T>(
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

    fun wasSignInSkipped(): Boolean = localDataSource.signInSkipped

}

/**
 * Abstract data source for sign in data.
 */
internal interface GoogleSignInDataSource<T> {
    fun getSignInInitData(): T

    suspend fun signIn(data: T): User?

    fun getSignedInUser(): User?
}

/**
 * Abstract data source for locally cached data.
 */
internal interface LocalDataSource {
    var signInSkipped: Boolean
}

/**
 * Supported sign in auth providers.
 */
internal sealed class AuthProvider {
    object Google : AuthProvider()
}