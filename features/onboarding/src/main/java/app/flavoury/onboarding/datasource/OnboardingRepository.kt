package app.flavoury.onboarding.datasource

import app.flavoury.onboarding.domain.OnboardingFlow
import flavoury.libraries.core.domain.User

internal class OnboardingRepository(private val userDataSource: UserDataSource) {

    /**
     * Returns an [OnboardingFlow] based on previously saved user data, if present.
     * If not, returns the default flow for new users.
     */
    suspend fun getOnboardingFlow(signedInUser: User): OnboardingFlow {
        return OnboardingFlow(userDataSource.getUser(signedInUser.uid) ?: signedInUser)
    }

}

/**
 * Abstract data source for user data.
 */
internal interface UserDataSource {
    suspend fun getUser(uid: String): User?
}