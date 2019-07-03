package app.flavoury

import android.content.Context
import app.flavoury.signin.RealSignInInfoSource
import app.flavoury.signin.SignInInfoSource


/**
 * Basic functions for dependency injection. Temporary, until Dagger is added.
 */

internal fun provideSignInInfoSource(context: Context): SignInInfoSource = RealSignInInfoSource(context)