package com.mirceabucerzan.core

import android.content.Context
import android.content.Intent

/**
 * Class that provides implicit "open" intents for the app's different features.
 */
object Actions {

    /* Intent filter actions for each feature's entry point activity */
    private const val ACTION_OPEN_SIGN_IN = "app.flavoury.signin.open"

    private lateinit var packageName: String

    @Synchronized
    fun init(context: Context) {
        if (!this::packageName.isInitialized) {
            packageName = context.applicationContext.packageName
        }
    }

    fun openSignInIntent(flags: Int? = null) = internalIntent(ACTION_OPEN_SIGN_IN, flags)

    /**
     * Returns an implicit [Intent] with the given action and flags, restricted to the application's package.
     */
    private fun internalIntent(action: String, intentFlags: Int? = null): Intent {
        if (!this::packageName.isInitialized) {
            CoreLog.e("Actions object not initialized, did you call init()?")
            throw IllegalStateException("Actions object not initialized")
        }
        return Intent(action).apply {
            setPackage(packageName)
            intentFlags?.let { flags = intentFlags }
        }
    }

}