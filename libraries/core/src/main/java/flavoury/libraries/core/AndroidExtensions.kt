package flavoury.libraries.core

import android.app.Activity
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Extension functions for Android SDK components.
 */

/**
 * [Fragment] extensions
 */

inline fun <reified VM : ViewModel> Fragment.getViewModel(provider: ViewModelProvider.Factory): VM {
    return ViewModelProviders.of(this, provider).get(VM::class.java)
}

inline fun <reified VM : ViewModel> Fragment.getActivityViewModel(provider: ViewModelProvider.Factory): VM {
    return requireActivity().getViewModel(provider)
}

/**
 * [Activity] extensions
 */

inline fun <reified VM : ViewModel> FragmentActivity.getViewModel(provider: ViewModelProvider.Factory): VM {
    return ViewModelProviders.of(this, provider).get(VM::class.java)
}

/**
 * View extensions
 */

fun ImageView.loadFromUrl(
    fragment: Fragment,
    url: Uri?,
    circleCrop: Boolean = false,
    @DrawableRes placeholderResId: Int? = null
) {
    Glide.with(fragment).load(url).apply {
        placeholderResId?.apply { placeholder(placeholderResId) }
        if (circleCrop) apply(RequestOptions.circleCropTransform())
        into(this@loadFromUrl)
    }
}