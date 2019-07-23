package app.flavoury.onboarding.presentation

import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import app.flavoury.onboarding.R
import app.flavoury.onboarding.usecases.OnboardingListItem
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_preference_large.*

/**
 * [RecyclerView.Adapter] for the onboarding preference screens.
 */
internal class OnboardingListAdapter(
    private val listener: ItemSelectedListener,
    private val largeItems: Boolean
) : ListAdapter<OnboardingListItem, OnboardingListItemViewHolder>(DIFF_CALLBACK) {

    interface ItemSelectedListener {
        fun onItemSelected(item: OnboardingListItem)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OnboardingListItem>() {
            override fun areItemsTheSame(oldItem: OnboardingListItem, newItem: OnboardingListItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: OnboardingListItem, newItem: OnboardingListItem): Boolean {
                return oldItem == newItem && oldItem.selected == newItem.selected
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingListItemViewHolder {
        return OnboardingListItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                if (largeItems) R.layout.list_item_preference_large else R.layout.list_item_preference_small,
                parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: OnboardingListItemViewHolder, position: Int) {
        if (position in 0 until itemCount) holder.bind(getItem(position))
    }
}

internal class OnboardingListItemViewHolder(
    view: View,
    private val listener: OnboardingListAdapter.ItemSelectedListener
) : RecyclerView.ViewHolder(view), LayoutContainer {

    override val containerView: View?
        get() = itemView
    private val ovalStrokeDrawable: Drawable? = itemView.context.getDrawable(R.drawable.shape_oval_stroke)

    init {
        preference_item_image.setOnClickListener {
            (itemView.tag as? OnboardingListItem)?.let { listener.onItemSelected(it) }
        }
    }

    fun bind(item: OnboardingListItem) {
        itemView.tag = item
        val context = itemView.context
        preference_item_label.setText(item.labelId)
        val dietImageDrawable = context.getDrawable(item.imageId)
        preference_item_image.setImageDrawable(
            if (item.selected) {
                LayerDrawable(arrayOf(dietImageDrawable, ovalStrokeDrawable))
            } else {
                dietImageDrawable
            }
        )
    }
}