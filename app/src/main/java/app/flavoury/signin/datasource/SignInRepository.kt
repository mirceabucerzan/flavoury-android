package app.flavoury.signin.datasource

import app.flavoury.signin.domain.User

/**
 * Repository that handles sign in data operations.
 */
class SignInRepository<T>(private val googleSignInDataSource: GoogleSignInDataSource<T>) {

    fun getSignInInitData(authProvider: AuthProvider): T {
        return when (authProvider) {
            is AuthProvider.Google -> googleSignInDataSource.getSignInInitData()
        }
    }

    suspend fun signIn(authProvider: AuthProvider, data: T): User? {
        return when (authProvider) {
            is AuthProvider.Google -> googleSignInDataSource.signIn(data)
        }
    }

}

/**
 * Abstract data source for sign in data.
 */
interface GoogleSignInDataSource<T> {
    fun getSignInInitData(): T

    suspend fun signIn(data: T): User?
}

/**
 * Supported sign in auth providers.
 */
sealed class AuthProvider {
    object Google : AuthProvider()
}