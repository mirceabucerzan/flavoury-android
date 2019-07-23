package app.flavoury.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import app.flavoury.onboarding.presentation.OnboardingViewModel
import flavoury.libraries.core.UniqueEventObserver
import flavoury.libraries.core.domain.EXTRA_USER
import flavoury.libraries.core.domain.User
import flavoury.libraries.core.getViewModel
import kotlinx.android.synthetic.main.activity_onboarding.*

/**
 * The onboarding feature's single entry point. Listens for navigation
 * changes and reacts by adding the appropriate Fragment.
 */
internal class OnboardingActivity : AppCompatActivity() {

    private lateinit var user: User
    private lateinit var viewModel: OnboardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        onboarding_nav_host_fragment.view?.setBackgroundResource(R.drawable.activity_onboarding_background)

        user = intent.getParcelableExtra(EXTRA_USER)

        viewModel = getViewModel(provideOnboardingViewModelFactory())
        viewModel.start(user)

        viewModel.navDirections.observe(this, UniqueEventObserver { navDirections ->
            navDirections?.let {
                findNavController(R.id.onboarding_nav_host_fragment).navigate(it)
            }
        })

        viewModel.error.observe(this, UniqueEventObserver {
            Toast.makeText(this, R.string.generic_error_message, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.onBackPressed()
    }
}