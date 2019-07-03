package app.flavoury.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import app.flavoury.onboarding.presentation.FlowStep
import app.flavoury.onboarding.presentation.LoadingFragmentDirections
import app.flavoury.onboarding.presentation.OnboardingViewModel
import flavoury.libraries.core.UniqueEventObserver
import flavoury.libraries.core.domain.EXTRA_USER
import flavoury.libraries.core.domain.User
import flavoury.libraries.core.getViewModel
import kotlinx.android.synthetic.main.activity_onboarding.*

/**
 * The onboarding feature's single entry point. Listens for flow step
 * changes and reacts by navigating to the appropriate Fragment.
 */
internal class OnboardingActivity : AppCompatActivity() {

    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
        onboarding_nav_host_fragment.view?.setBackgroundResource(R.drawable.activity_onboarding_background)

        user = intent.getParcelableExtra(EXTRA_USER)

        val viewModel: OnboardingViewModel = getViewModel(provideOnboardingViewModelFactory(user))
        viewModel.flowStep.observe(this, Observer { navigate(it) })
        viewModel.flowError.observe(this, UniqueEventObserver {
            Toast.makeText(this, R.string.generic_error_message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun navigate(flowStep: FlowStep) {
        findNavController(R.id.onboarding_nav_host_fragment).navigate(
            when (flowStep) {
                FlowStep.Diet -> LoadingFragmentDirections.actionDiet(user)
            }
        )
    }

}