package app.flavoury.onboarding.presentation

import android.os.Bundle
import android.view.View
import app.flavoury.onboarding.R

/**
 * View displaying the end of the onboarding flow.
 */
internal class AllDoneFragment : OnboardingFragment() {

    override val titleResId: Int
        get() = R.string.onboarding_all_done_title
    override val buttonLabelResId: Int?
        get() = R.string.onboarding_finish_button_label
    override val contentLayoutResId: Int
        get() = R.layout.fragment_all_done

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAdvanceButton()
    }

}