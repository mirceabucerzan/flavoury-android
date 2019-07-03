package app.flavoury.onboarding.presentation

import app.flavoury.onboarding.R

/**
 * View displaying an indeterminate loading indicator.
 */
internal class LoadingFragment : OnboardingFragment() {

    override val titleResId: Int
        get() = R.string.onboarding_loading_title
    override val buttonLabelResId: Int?
        get() = null
    override val contentLayoutResId: Int
        get() = R.layout.fragment_loading

}