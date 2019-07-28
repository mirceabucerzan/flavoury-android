package app.flavoury.onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import app.flavoury.onboarding.R
import app.flavoury.onboarding.provideOnboardingViewModelFactory
import flavoury.libraries.core.getActivityViewModel
import kotlinx.android.synthetic.main.fragment_onboarding.*
import kotlinx.android.synthetic.main.fragment_onboarding.view.*

/**
 * Base class for the Onboarding views. Displays a title up top, a
 * content placeholder in the middle and a button beneath it.
 */
internal abstract class OnboardingFragment : Fragment() {

    @get:StringRes
    protected abstract val titleResId: Int

    @get:StringRes
    protected abstract val buttonLabelResId: Int?

    @get:LayoutRes
    protected abstract val contentLayoutResId: Int

    protected lateinit var viewModel: OnboardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getActivityViewModel(provideOnboardingViewModelFactory())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.fragment_onboarding, container, false)
        root.onboarding_title.text = getString(titleResId)
        buttonLabelResId?.let { root.onboarding_advance_button.text = getString(it) }
        root.onboarding_content_container.layoutResource = contentLayoutResId
        root.onboarding_content_container.inflate()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onboarding_advance_button.setOnClickListener {
            hideAdvanceButton()
            viewModel.advanceOnboardingFlow()
        }
    }

    fun showAdvanceButton() {
        onboarding_advance_button.visibility = View.VISIBLE
    }

    fun hideAdvanceButton() {
        onboarding_advance_button.visibility = View.INVISIBLE
    }

    fun showAdvanceSecondaryButton(@StringRes labelId: Int, clickListener: () -> Unit) {
        onboarding_advance_secondary_button.setText(labelId)
        onboarding_advance_secondary_button.visibility = View.VISIBLE
        onboarding_advance_secondary_button.setOnClickListener { clickListener() }
    }

    fun updateTitle(title: String) {
        onboarding_title.text = title
    }

}