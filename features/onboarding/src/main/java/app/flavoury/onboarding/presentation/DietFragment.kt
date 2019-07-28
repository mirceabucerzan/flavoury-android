package app.flavoury.onboarding.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.flavoury.onboarding.R
import app.flavoury.onboarding.usecases.OnboardingListItem
import kotlinx.android.synthetic.main.fragment_list.*

/**
 * View which displays the diets the user can choose from in a 2x2 grid.
 */
internal class DietFragment : OnboardingFragment(), OnboardingListAdapter.ItemSelectedListener {

    companion object {
        private const val NUM_COLUMNS = 2
    }

    override val titleResId: Int
        get() = R.string.onboarding_diet_preference_title
    override val buttonLabelResId: Int?
        get() = R.string.onboarding_next_button_label
    override val contentLayoutResId: Int
        get() = R.layout.fragment_list

    private var listAdapter: ListAdapter<OnboardingListItem, OnboardingListItemViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideAdvanceButton()
        val viewLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(context, NUM_COLUMNS)
        listAdapter = OnboardingListAdapter(this, true)
        onboarding_list.apply {
            layoutManager = viewLayoutManager
            adapter = listAdapter
        }

        viewModel.dietListItems.observe(viewLifecycleOwner, Observer { listItems ->
            if (listItems.find { it.selected } != null) {
                // once a selection is made, give the user the chance to advance the flow
                showAdvanceButton()
            } else {
                hideAdvanceButton()
            }
            listAdapter?.submitList(listItems)
        })
    }

    override fun onItemSelected(item: OnboardingListItem) {
        viewModel.selectDiet(item)
    }
}