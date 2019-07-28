package app.flavoury.onboarding.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import app.flavoury.onboarding.R
import flavoury.libraries.core.loadFromUrl
import kotlinx.android.synthetic.main.fragment_welcome_back.*

/**
 * View displayed for returning users, allowing them to skip onboarding.
 */
internal class WelcomeBackFragment : OnboardingFragment() {

    override val titleResId: Int
        get() = R.string.onboarding_welcome_back_title
    override val buttonLabelResId: Int?
        get() = R.string.onboarding_update_button_label
    override val contentLayoutResId: Int
        get() = R.layout.fragment_welcome_back

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAdvanceButton()
        showAdvanceSecondaryButton(R.string.onboarding_recipes_button_label) { viewModel.advanceToRecipes() }

        viewModel.userNameAndPhoto.observe(viewLifecycleOwner, Observer {
            updateTitle(getString(R.string.onboarding_welcome_back_title, it.first))
            onboarding_profile_image.loadFromUrl(this, it.second, true, R.drawable.icon_person_generic)
        })
    }

}