package com.mirceabucerzan.core

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

/**
 * Extension functions for Android SDK components.
 */

/**
 * [Fragment] extensions
 */

inline fun <reified VM : ViewModel> Fragment.getViewModel(provider: ViewModelProvider.Factory): VM {
    return ViewModelProviders.of(this, provider).get(VM::class.java)
}