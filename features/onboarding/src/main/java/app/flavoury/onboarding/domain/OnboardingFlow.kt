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
            setOf(
                Omnivore(),
                Vegetarian(),
                Vegan(),
                Paleo()
            ), user.diet
        )

        onboardingSteps += Step.IntolerancesPreference(
            setOf(
                Dairy(),
                Egg(),
                Gluten(),
                Nut(),
                Sesame(),
                Seafood(),
                Shellfish(),
                Soy(),
                Wheat()
            ), user.intolerances
        )

        steps = onboardingSteps
    }

    override fun toString(): String {
        return "OnboardingFlow(steps=$steps)"
    }
}

internal sealed class Step {
    /**
     * @property allDiets Set of all the existing [Diet]s a user can choose from.
     * @property userDiet The user's preferred [Diet].
     */
    class DietPreference(val allDiets: Set<Diet>, val userDiet: Diet) : Step()

    /**
     * @property allIntolerances List of all the existing [Intolerance]s a user can choose from.
     * @property userIntolerances The user's existing [Intolerance]s.
     */
    class IntolerancesPreference(
        val allIntolerances: Set<Intolerance>,
        val userIntolerances: Set<Intolerance>
    ) : Step()
}