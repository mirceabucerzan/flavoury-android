package app.flavoury.onboarding.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import app.flavoury.onboarding.R
import app.flavoury.onboarding.usecases.OnboardingListItem
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * View which displays the intolerances the user can choose from in a 3x3 grid.
 */
internal class IntolerancesFragment : OnboardingFragment(), OnboardingListAdapter.ItemSelectedListener {

    companion object {
        private const val NUM_COLUMNS = 3
    }

    override val titleResId: Int
        get() = R.string.onboarding_intolerances_preference_title
    override val buttonLabelResId: Int?
        get() = R.string.onboarding_next_button_label
    override val contentLayoutResId: Int
        get() = R.layout.fragment_list

    private var listAdapter: ListAdapter<OnboardingListItem, OnboardingListItemViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showAdvanceButton()
        val viewLayoutManager = GridLayoutManager(context, NUM_COLUMNS)
        listAdapter = OnboardingListAdapter(this, false)
        onboarding_list.apply {
            layoutManager = viewLayoutManager
            adapter = listAdapter
        }
        viewModel.intoleranceListItems.observe(viewLifecycleOwner, Observer { listAdapter?.submitList(it) })
    }

    override fun onItemSelected(item: OnboardingListItem) {
        viewModel.selectIntolerance(item)
    }
}