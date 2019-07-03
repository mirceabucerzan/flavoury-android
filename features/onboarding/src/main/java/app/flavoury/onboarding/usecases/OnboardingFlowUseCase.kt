package app.flavoury.onboarding.usecases

import app.flavoury.onboarding.datasource.OnboardingRepository
import app.flavoury.onboarding.domain.OnboardingFlow
import flavoury.libraries.core.UseCase
import flavoury.libraries.core.domain.User

/**
 * [UseCase] that queries the [OnboardingRepository] on a background thread for data.
 */
internal class OnboardingFlowUseCase(private val repository: OnboardingRepository) : UseCase<User, OnboardingFlow>() {

    override val operationType: OperationType
        get() = OperationType.IO()

    override suspend fun execute(parameters: User): OnboardingFlow = repository.getOnboardingFlow(parameters)

}