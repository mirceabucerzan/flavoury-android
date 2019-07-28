package app.flavoury.onboarding.domain

import android.net.Uri
import flavoury.libraries.core.CoreLog
import flavoury.libraries.core.domain.*

/**
 * Domain model for the Onboarding feature which contains the business logic for
 * determining the exact sequence of steps in the flow for a particular user.
 */
internal class OnboardingFlow(user: User) {

    val steps: List<Step>

    init {
        CoreLog.d("OnboardingFlow init: user = $user")
        val onboardingSteps = mutableListOf<Step>()

        if (user.diet !is Unknown) {
            CoreLog.d("User already has a diet, adding welcome screen to the flow.")
            onboardingSteps += Step.WelcomeBack(user.fullName, user.photoUrl)
        }

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

        onboardingSteps += Step.FinalInfo

        steps = onboardingSteps
    }

    override fun toString(): String {
        return "OnboardingFlow(steps=$steps)"
    }
}

internal sealed class Step {

    /**
     * @property fullName The user's name.
     * @property photoUrl The user's profile photo, or null.
     */
    class WelcomeBack(val fullName: String, val photoUrl: Uri?) : Step()

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

    object FinalInfo : Step()
}