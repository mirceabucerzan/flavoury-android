package app.flavoury.onboarding.datasource

import app.flavoury.onboarding.domain.OnboardingFlow
import com.mirceabucerzan.core.domain.User

class OnboardingRepository(private val userDataSource: UserDataSource) {

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
interface UserDataSource {
    suspend fun getUser(uid: String): User?
}