package app.flavoury.onboarding.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import app.flavoury.onboarding.R
import app.flavoury.onboarding.domain.Step
import flavoury.libraries.core.domain.*

/**
 * Ui model for the Diet Preference screen.
 */
internal sealed class DietListItem(
    @StringRes val labelId: Int,
    @DrawableRes val imageId: Int,
    var selected: Boolean,
    val displayIndex: Int
) {
    class Omnivore(
        labelId: Int = R.string.onboarding_diet_omnivore_label,
        imageId: Int = R.drawable.icon_omnivore,
        selected: Boolean = false,
        displayIndex: Int = 0
    ) : DietListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): DietListItem = Omnivore(labelId, imageId, selected, displayIndex)
    }

    class Vegetarian(
        labelId: Int = R.string.onboarding_diet_vegetarian_label,
        imageId: Int = R.drawable.icon_vegetarian,
        selected: Boolean = false,
        displayIndex: Int = 1
    ) : DietListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): DietListItem = Vegetarian(labelId, imageId, selected, displayIndex)
    }

    class Vegan(
        labelId: Int = R.string.onboarding_diet_vegan_label,
        imageId: Int = R.drawable.icon_vegan,
        selected: Boolean = false,
        displayIndex: Int = 2
    ) : DietListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): DietListItem = Vegan(labelId, imageId, selected, displayIndex)
    }

    class Paleo(
        labelId: Int = R.string.onboarding_diet_paleo_label,
        imageId: Int = R.drawable.icon_paleo,
        selected: Boolean = false,
        displayIndex: Int = 3
    ) : DietListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): DietListItem = Paleo(labelId, imageId, selected, displayIndex)
    }

    class Unknown(
        labelId: Int = -1,
        imageId: Int = -1,
        selected: Boolean = false,
        displayIndex: Int = -1
    ) : DietListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): DietListItem = Unknown(labelId, imageId, selected, displayIndex)
    }

    abstract fun copy(): DietListItem

    override fun toString(): String {
        return "${javaClass.simpleName}(selected=$selected)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DietListItem) return false
        if (labelId != other.labelId) return false
        if (imageId != other.imageId) return false
        if (displayIndex != other.displayIndex) return false
        return true
    }

    override fun hashCode(): Int {
        var result = labelId
        result = 31 * result + imageId
        result = 31 * result + displayIndex
        return result
    }
}

/**
 * Convert the [Step.DietPreference] domain model to the UI model.
 */
internal fun Step.DietPreference.toDietListItems(): List<DietListItem> {
    return diets.map {
        it.toDietListItem().apply { selected = it == this@toDietListItems.currentDiet }
    }.sortedBy { it.displayIndex }
}

/**
 * Convert the [Diet] domain model to the UI model.
 */
internal fun Diet.toDietListItem(): DietListItem {
    return when (this) {
        is Omnivore -> DietListItem.Omnivore()
        is Vegetarian -> DietListItem.Vegetarian()
        is Vegan -> DietListItem.Vegan()
        is Paleo -> DietListItem.Paleo()
        is Unknown -> DietListItem.Unknown()
    }
}