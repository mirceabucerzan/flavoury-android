package app.flavoury.signin.usecases

import app.flavoury.signin.datasource.SignInRepository
import flavoury.libraries.core.UseCase

internal class SkipSignInUseCase<T>(private val repository: SignInRepository<T>) : UseCase<Unit, Unit>() {
    override val operationType: OperationType
        get() = OperationType.Simple()

    override suspend fun execute(parameters: Unit) {
        repository.skipSignIn()
    }
}