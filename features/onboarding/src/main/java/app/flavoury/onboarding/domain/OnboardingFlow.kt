package app.flavoury.onboarding.domain

import flavoury.libraries.core.domain.*

/**
 * Domain model for the Onboarding feature which contains the business logic for
 * determining the exact sequence of steps in the flow for a particular user.
 */
internal class OnboardingFlow(user: User) {

    val steps: List<Step>

    init {
        val onboardingSteps = mutableListOf<Step>()
        onboardingSteps += Step.DietPreference(
            listOf(
                Omnivore(),
                Vegetarian(),
                Vegan(),
                Paleo()
            ), user.diet
        )
        steps = onboardingSteps
    }

    override fun toString(): String {
        return "OnboardingFlow(steps=$steps)"
    }
}

internal sealed class Step {
    /**
     * @property diets List of all the existing [Diet]s a user can choose from.
     * @property currentDiet The user's preferred [Diet].
     */
    class DietPreference(val diets: List<Diet>, val currentDiet: Diet) : Step()
}