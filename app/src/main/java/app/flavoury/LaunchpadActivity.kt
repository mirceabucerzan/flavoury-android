package app.flavoury

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import app.flavoury.signin.datasource.SignInRepository
import app.flavoury.signin.domain.User
import com.mirceabucerzan.core.Actions
import com.mirceabucerzan.core.CoreLog

/**
 * Launcher activity with the sole purpose to redirect to the appropriate feature.
 */
class LaunchpadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val signInRepo: SignInRepository<Intent> = provideSignInRepository(this)
        val user: User? = signInRepo.getSignedInUser()
        when {
            user != null -> {
                CoreLog.d("User is already signed in: $user")
                // TODO onboarding check
            }
            signInRepo.isSignInSkipped() -> {
                CoreLog.d("User has previously skipped sign in")
                // TODO navigate to recipe discovery
            }
            else -> {
                CoreLog.d("Starting the sign in feature")
                startActivity(
                    Actions.openSignInIntent(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                )
            }
        }
    }

}