package app.flavoury

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.flavoury.signin.datasource.SignInRepository
import com.mirceabucerzan.core.Actions
import com.mirceabucerzan.core.CoreLog
import com.mirceabucerzan.core.domain.User

/**
 * Launcher activity with the sole purpose to redirect to the appropriate feature.
 */
class LaunchpadActivity : AppCompatActivity() {

    companion object {
        private const val FLAGS_NEW_TASK: Int = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signInRepo: SignInRepository<Intent> = provideSignInRepository(this)
        val user: User? = signInRepo.getSignedInUser()
        when {
            user != null -> {
                CoreLog.d("User is already signed in: $user")
                // TODO onboarding check, pass id to onboarding repo
                startActivity(Actions.openOnboardingIntent(user, FLAGS_NEW_TASK))
            }
            signInRepo.isSignInSkipped() -> {
                CoreLog.d("User has previously skipped sign in")
                // TODO navigate to recipe discovery
            }
            else -> {
                CoreLog.d("Starting the sign in feature")
                startActivity(Actions.openSignInIntent(FLAGS_NEW_TASK))
            }
        }
    }

}