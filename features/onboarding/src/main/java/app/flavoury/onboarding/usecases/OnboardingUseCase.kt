package app.flavoury.onboarding.usecases

import android.net.Uri
import app.flavoury.onboarding.datasource.OnboardingRepository
import app.flavoury.onboarding.domain.OnboardingFlow
import app.flavoury.onboarding.domain.Step
import flavoury.libraries.core.UseCase
import flavoury.libraries.core.domain.User

/**
 * [UseCase] that queries the [OnboardingRepository] on a background thread for
 * data and processes it for the presentation layer.
 */
internal class OnboardingUseCase(private val repository: OnboardingRepository) : UseCase<User, OnboardingModel>() {

    override val operationType: OperationType
        get() = OperationType.IO()

    override suspend fun execute(parameters: User): OnboardingModel {
        val onboardingFlow = repository.getOnboardingFlow(parameters)
        val welcomeBackStep = onboardingFlow.steps.find { it is Step.WelcomeBack } as? Step.WelcomeBack
        val fullName = welcomeBackStep?.fullName.orEmpty()
        val photoUri = welcomeBackStep?.photoUrl
        return OnboardingModel(
            onboardingFlow.steps.map { it.toFlowStep() },
            fullName,
            photoUri,
            getDietListItems(onboardingFlow),
            getIntoleranceListItems(onboardingFlow)
        )
    }

    private fun getDietListItems(onboardingFlow: OnboardingFlow): List<OnboardingListItem> {
        val dietPreference = onboardingFlow.steps.find { it is Step.DietPreference }
        return (dietPreference as? Step.DietPreference)?.toOnboardingListItems() ?: emptyList()
    }

    private fun getIntoleranceListItems(onboardingFlow: OnboardingFlow): List<OnboardingListItem> {
        val intolerancesPreference = onboardingFlow.steps.find { it is Step.IntolerancesPreference }
        return (intolerancesPreference as? Step.IntolerancesPreference)?.toOnboardingListItems() ?: emptyList()
    }

}

internal class OnboardingModel(
    val flowSteps: List<FlowStep>,
    val fullName: String,
    val photoUrl: Uri?,
    val dietListItems: List<OnboardingListItem>,
    val intoleranceListItems: List<OnboardingListItem>
)

internal sealed class FlowStep {
    object WelcomeBack : FlowStep()
    object Diet : FlowStep()
    object Intolerances : FlowStep()
    object AllDone : FlowStep()
}

internal fun Step.toFlowStep(): FlowStep {
    return when (this) {
        is Step.WelcomeBack -> FlowStep.WelcomeBack
        is Step.DietPreference -> FlowStep.Diet
        is Step.IntolerancesPreference -> FlowStep.Intolerances
        is Step.FinalInfo -> FlowStep.AllDone
    }
}