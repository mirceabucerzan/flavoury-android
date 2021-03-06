package app.flavoury.signin.usecases

import app.flavoury.signin.datasource.AuthProvider
import app.flavoury.signin.datasource.SignInRepository
import flavoury.libraries.core.UseCase
import flavoury.libraries.core.domain.User

/**
 * [UseCase] that triggers sign in initialization.
 */
internal class GoogleInitSignInUseCase<T>(private val signInRepository: SignInRepository<T>) : UseCase<Unit, T>() {

    override val operationType: OperationType
        get() = OperationType.Simple()

    override suspend fun execute(parameters: Unit): T {
        return signInRepository.getSignInInitData(AuthProvider.Google)
    }
}

/**
 * [UseCase] that triggers the actual sign in.
 */
internal class GooglePerformSignInUseCase<T>(private val signInRepository: SignInRepository<T>) : UseCase<T, User>() {

    override val operationType: OperationType
        get() = OperationType.IO()

    @Throws(GoogleSignInException::class)
    override suspend fun execute(parameters: T): User {
        return signInRepository.signIn(AuthProvider.Google, parameters) ?: throw GoogleSignInException()
    }
}

/**
 * [Exception] thrown if sign in fails.
 */
internal class GoogleSignInException : RuntimeException()