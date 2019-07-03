package app.flavoury.onboarding.presentation

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.flavoury.onboarding.R
import app.flavoury.onboarding.provideOnboardingViewModelFactory
import flavoury.libraries.core.getViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_diet.*
import kotlinx.android.synthetic.main.list_item_diet.*

/**
 * View which displays the diets the user can choose from in a 2x2 grid.
 */
internal class DietFragment : OnboardingFragment(), DietListAdapter.ItemSelectedListener {

    companion object {
        private const val NUM_COLUMNS = 2
    }

    override val titleResId: Int
        get() = R.string.onboarding_diet_preference_title
    override val buttonLabelResId: Int?
        get() = R.string.onboarding_next_button_label
    override val contentLayoutResId: Int
        get() = R.layout.fragment_diet

    private val args: DietFragmentArgs by navArgs()
    private lateinit var viewModel: OnboardingViewModel
    private var listAdapter: ListAdapter<DietListItem, DietListItemViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = getViewModel(provideOnboardingViewModelFactory(args.user))
        viewModel.dietListItems.observe(this, Observer { dietListItems ->
            if (dietListItems.find { it.selected } != null) {
                // once a selection is made, give the user the chance to advance the flow
                showAdvanceButton()
            } else {
                hideAdvanceButton()
            }
            listAdapter?.submitList(dietListItems)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewLayoutManager = GridLayoutManager(context, NUM_COLUMNS)
        listAdapter = DietListAdapter(this)
        diet_list.apply {
            layoutManager = viewLayoutManager
            adapter = listAdapter
        }
    }

    override fun onItemSelected(item: DietListItem) {
        viewModel.selectDiet(item)
    }
}

internal class DietListAdapter(private val listener: ItemSelectedListener) :
    ListAdapter<DietListItem, DietListItemViewHolder>(DIFF_CALLBACK) {

    interface ItemSelectedListener {
        fun onItemSelected(item: DietListItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DietListItem>() {
            override fun areItemsTheSame(oldItem: DietListItem, newItem: DietListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DietListItem, newItem: DietListItem): Boolean {
                return oldItem == newItem && oldItem.selected == newItem.selected
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DietListItemViewHolder {
        return DietListItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_item_diet,
                parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: DietListItemViewHolder, position: Int) {
        if (position in 0 until itemCount) holder.bind(getItem(position))
    }
}

internal class DietListItemViewHolder(view: View, private val listener: DietListAdapter.ItemSelectedListener) :
    RecyclerView.ViewHolder(view), LayoutContainer {
    override val containerView: View?
        get() = itemView
    private val ovalStrokeDrawable: Drawable? = itemView.context.getDrawable(R.drawable.shape_oval_stroke)

    init {
        onboarding_diet_image.setOnClickListener {
            (itemView.tag as? DietListItem)?.let { listener.onItemSelected(it) }
        }
    }

    fun bind(item: DietListItem) {
        itemView.tag = item
        val context = itemView.context
        onboarding_diet_label.setText(item.labelId)
        val dietImageDrawable = context.getDrawable(item.imageId)
        onboarding_diet_image.setImageDrawable(
            if (item.selected) {
                LayerDrawable(arrayOf(dietImageDrawable, ovalStrokeDrawable))
            } else {
                dietImageDrawable
            }
        )
    }
}