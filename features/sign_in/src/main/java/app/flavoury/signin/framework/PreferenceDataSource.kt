package app.flavoury.signin.framework

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import app.flavoury.signin.datasource.LocalDataSource

/**
 * Data source which uses [SharedPreferences] for storing its data.
 */
internal class PreferenceDataSource(context: Context) : LocalDataSource {

    companion object {
        private const val PREFS_NAME = "SIGN_IN_PREFERENCES"
        private const val KEY_SIGN_IN_SKIPPED = "KEY_SIGN_IN_SKIPPED"
    }

    private val prefs: SharedPreferences = context.applicationContext.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

    override var signInSkipped: Boolean
        get() = prefs.getBoolean(KEY_SIGN_IN_SKIPPED, false)
        set(value) = prefs.edit { putBoolean(KEY_SIGN_IN_SKIPPED, value) }

}