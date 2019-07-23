package app.flavoury.onboarding.usecases

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import app.flavoury.onboarding.R
import app.flavoury.onboarding.domain.Step
import flavoury.libraries.core.domain.*

/**
 * Ui model for the Onboarding Preference screens.
 */
internal sealed class OnboardingListItem(
    @StringRes val labelId: Int,
    @DrawableRes val imageId: Int,
    var selected: Boolean,
    val displayIndex: Int
) {
    /**
     * Diet list items.
     */

    class Omnivore(
        labelId: Int = R.string.onboarding_diet_omnivore_label,
        imageId: Int = R.drawable.icon_omnivore,
        selected: Boolean = false,
        displayIndex: Int = 0
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Omnivore(labelId, imageId, selected, displayIndex)
    }

    class Vegetarian(
        labelId: Int = R.string.onboarding_diet_vegetarian_label,
        imageId: Int = R.drawable.icon_vegetarian,
        selected: Boolean = false,
        displayIndex: Int = 1
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Vegetarian(labelId, imageId, selected, displayIndex)
    }

    class Vegan(
        labelId: Int = R.string.onboarding_diet_vegan_label,
        imageId: Int = R.drawable.icon_vegan,
        selected: Boolean = false,
        displayIndex: Int = 2
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Vegan(labelId, imageId, selected, displayIndex)
    }

    class Paleo(
        labelId: Int = R.string.onboarding_diet_paleo_label,
        imageId: Int = R.drawable.icon_paleo,
        selected: Boolean = false,
        displayIndex: Int = 3
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Paleo(labelId, imageId, selected, displayIndex)
    }

    class Unknown(
        labelId: Int = -1,
        imageId: Int = -1,
        selected: Boolean = false,
        displayIndex: Int = -1
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Unknown(labelId, imageId, selected, displayIndex)
    }

    /**
     * Intolerance list items.
     */

    class Dairy(
        labelId: Int = R.string.onboarding_intolerance_dairy_label,
        imageId: Int = R.drawable.icon_dairy,
        selected: Boolean = false,
        displayIndex: Int = 0
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Dairy(labelId, imageId, selected, displayIndex)
    }

    class Egg(
        labelId: Int = R.string.onboarding_intolerance_egg_label,
        imageId: Int = R.drawable.icon_egg,
        selected: Boolean = false,
        displayIndex: Int = 1
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Egg(labelId, imageId, selected, displayIndex)
    }

    class Gluten(
        labelId: Int = R.string.onboarding_intolerance_gluten_label,
        imageId: Int = R.drawable.icon_gluten,
        selected: Boolean = false,
        displayIndex: Int = 2
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Gluten(labelId, imageId, selected, displayIndex)
    }

    class Nut(
        labelId: Int = R.string.onboarding_intolerance_nut_label,
        imageId: Int = R.drawable.icon_nut,
        selected: Boolean = false,
        displayIndex: Int = 3
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Nut(labelId, imageId, selected, displayIndex)
    }

    class Sesame(
        labelId: Int = R.string.onboarding_intolerance_sesame_label,
        imageId: Int = R.drawable.icon_sesame,
        selected: Boolean = false,
        displayIndex: Int = 4
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Sesame(labelId, imageId, selected, displayIndex)
    }

    class Seafood(
        labelId: Int = R.string.onboarding_intolerance_seafood_label,
        imageId: Int = R.drawable.icon_seafood,
        selected: Boolean = false,
        displayIndex: Int = 5
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Seafood(labelId, imageId, selected, displayIndex)
    }

    class Shellfish(
        labelId: Int = R.string.onboarding_intolerance_shellfish_label,
        imageId: Int = R.drawable.icon_shellfish,
        selected: Boolean = false,
        displayIndex: Int = 6
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Shellfish(labelId, imageId, selected, displayIndex)
    }

    class Soy(
        labelId: Int = R.string.onboarding_intolerance_soy_label,
        imageId: Int = R.drawable.icon_soy,
        selected: Boolean = false,
        displayIndex: Int = 7
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Soy(labelId, imageId, selected, displayIndex)
    }

    class Wheat(
        labelId: Int = R.string.onboarding_intolerance_wheat_label,
        imageId: Int = R.drawable.icon_wheat,
        selected: Boolean = false,
        displayIndex: Int = 8
    ) : OnboardingListItem(labelId, imageId, selected, displayIndex) {
        override fun copy(): OnboardingListItem =
            Wheat(labelId, imageId, selected, displayIndex)
    }

    abstract fun copy(): OnboardingListItem

    override fun toString(): String {
        return "${javaClass.simpleName}(selected=$selected)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is OnboardingListItem) return false
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
internal fun Step.DietPreference.toOnboardingListItems(): List<OnboardingListItem> {
    return allDiets.map {
        it.toOnboardingListItem().apply { selected = it == this@toOnboardingListItems.userDiet }
    }.sortedBy { it.displayIndex }
}

/**
 * Convert the [Diet] domain model to the UI model.
 */
internal fun Diet.toOnboardingListItem(): OnboardingListItem {
    return when (this) {
        is Omnivore -> OnboardingListItem.Omnivore()
        is Vegetarian -> OnboardingListItem.Vegetarian()
        is Vegan -> OnboardingListItem.Vegan()
        is Paleo -> OnboardingListItem.Paleo()
        is Unknown -> OnboardingListItem.Unknown()
    }
}

/**
 * Convert the [Step.IntolerancesPreference] domain model to the UI model.
 */
internal fun Step.IntolerancesPreference.toOnboardingListItems(): List<OnboardingListItem> {
    return allIntolerances.map {
        it.toOnboardingListItem().apply { selected = userIntolerances.contains(it) }
    }.sortedBy { it.displayIndex }
}

/**
 * Convert the [Intolerance] domain model to the UI model.
 */
internal fun Intolerance.toOnboardingListItem(): OnboardingListItem {
    return when (this) {
        is Dairy -> OnboardingListItem.Dairy()
        is Egg -> OnboardingListItem.Egg()
        is Gluten -> OnboardingListItem.Gluten()
        is Nut -> OnboardingListItem.Nut()
        is Sesame -> OnboardingListItem.Sesame()
        is Seafood -> OnboardingListItem.Seafood()
        is Shellfish -> OnboardingListItem.Shellfish()
        is Soy -> OnboardingListItem.Soy()
        is Wheat -> OnboardingListItem.Wheat()
    }
}