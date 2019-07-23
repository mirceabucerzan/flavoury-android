package flavoury.libraries.core.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val EXTRA_USER = "flavoury.libraries.core.extra.user"

/**
 * Business model for a Flavoury user.
 */
@Parcelize
data class User(
    val uid: String,
    val fullName: String,
    val email: String,
    val photoUrl: Uri?,
    val onboarded: Boolean = false,
    val diet: Diet = Unknown(),
    val intolerances: Set<Intolerance> = emptySet()
) : Parcelable

sealed class Diet(val name: String) : Parcelable {
    companion object {
        const val OMNIVORE = "omnivore"
        const val VEGETARIAN = "vegetarian"
        const val VEGAN = "vegan"
        const val PALEO = "paleo"
        const val UNKNOWN = "unknown"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Diet) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

@Parcelize
class Omnivore : Diet(OMNIVORE)

@Parcelize
class Vegetarian : Diet(VEGETARIAN)

@Parcelize
class Vegan : Diet(VEGAN)

@Parcelize
class Paleo : Diet(PALEO)

@Parcelize
class Unknown : Diet(UNKNOWN)

sealed class Intolerance(val name: String) : Parcelable {
    companion object {
        const val DAIRY = "dairy"
        const val EGG = "egg"
        const val GLUTEN = "gluten"
        const val NUT = "nut"
        const val SESAME = "sesame"
        const val SEAFOOD = "seafood"
        const val SHELLFISH = "shellfish"
        const val SOY = "soy"
        const val WHEAT = "wheat"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Intolerance) return false
        if (name != other.name) return false
        return true
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}

@Parcelize
class Dairy : Intolerance(DAIRY)

@Parcelize
class Egg : Intolerance(EGG)

@Parcelize
class Gluten : Intolerance(GLUTEN)

@Parcelize
class Nut : Intolerance(NUT)

@Parcelize
class Sesame : Intolerance(SESAME)

@Parcelize
class Seafood : Intolerance(SEAFOOD)

@Parcelize
class Shellfish : Intolerance(SHELLFISH)

@Parcelize
class Soy : Intolerance(SOY)

@Parcelize
class Wheat : Intolerance(WHEAT)