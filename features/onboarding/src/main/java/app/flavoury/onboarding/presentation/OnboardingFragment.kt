package app.flavoury.onboarding.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import app.flavoury.onboarding.R
import kotlinx.android.synthetic.main.fragment_onboarding.*
import kotlinx.android.synthetic.main.fragment_onboarding.view.*

/**
 * Base class for the Onboarding views. Displays a title up top, a
 * content placeholder in the middle and a button beneath it.
 */
abstract class OnboardingFragment : Fragment() {

    @get:StringRes
    protected abstract val titleResId: Int

    @get:StringRes
    protected abstract val buttonLabelResId: Int?

    @get:LayoutRes
    protected abstract val contentLayoutResId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root: View = inflater.inflate(R.layout.fragment_onboarding, container, false)
        root.onboarding_title.text = getString(titleResId)
        buttonLabelResId?.let { root.onboarding_advance_button.text = getString(it) }
        root.onboarding_content_container.layoutResource = contentLayoutResId
        root.onboarding_content_container.inflate()
        return root
    }

    fun showAdvanceButton() {
        onboarding_advance_button.visibility = View.VISIBLE
    }

    fun hideAdvanceButton() {
        onboarding_advance_button.visibility = View.INVISIBLE
    }

}