package com.mirceabucerzan.core.domain

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

const val EXTRA_USER = "com.mirceabucerzan.extra.user"

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
    val diet: Diet = Unknown()
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