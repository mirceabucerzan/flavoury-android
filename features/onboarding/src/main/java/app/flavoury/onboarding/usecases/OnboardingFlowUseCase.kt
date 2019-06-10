package app.flavoury.onboarding.usecases

import app.flavoury.onboarding.datasource.OnboardingRepository
import app.flavoury.onboarding.domain.OnboardingFlow
import com.mirceabucerzan.core.UseCase
import com.mirceabucerzan.core.domain.User

/**
 * [UseCase] that queries the [OnboardingRepository] on a background thread for data.
 */
class OnboardingFlowUseCase(private val repository: OnboardingRepository) : UseCase<User, OnboardingFlow>() {

    override val operationType: OperationType
        get() = OperationType.IO()

    override suspend fun execute(parameters: User): OnboardingFlow = repository.getOnboardingFlow(parameters)

}