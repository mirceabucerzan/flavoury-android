package app.flavoury

import android.content.Context
import android.content.Intent
import app.flavoury.signin.datasource.GoogleSignInDataSource
import app.flavoury.signin.datasource.LocalDataSource
import app.flavoury.signin.datasource.SignInRepository
import app.flavoury.signin.framework.GoogleFirebaseSignInDataSource
import app.flavoury.signin.framework.PreferenceDataSource


/**
 * Basic functions for dependency injection. Temporary, until Dagger is added.
 */

fun provideSignInRepository(context: Context): SignInRepository<Intent> {
    return SignInRepository(provideGoogleSignInDataSource(context), provideLocalDataSource(context))
}

fun provideGoogleSignInDataSource(context: Context): GoogleSignInDataSource<Intent> {
    return GoogleFirebaseSignInDataSource(context)
}

fun provideLocalDataSource(context: Context): LocalDataSource = PreferenceDataSource(context)