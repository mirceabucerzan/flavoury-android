package app.flavoury.signin

import android.content.Context
import android.content.Intent
import app.flavoury.signin.datasource.GoogleSignInDataSource
import app.flavoury.signin.datasource.LocalDataSource
import app.flavoury.signin.datasource.SignInRepository
import app.flavoury.signin.framework.GoogleFirebaseSignInDataSource
import app.flavoury.signin.framework.PreferenceDataSource
import app.flavoury.signin.presentation.SignInViewModelFactory
import app.flavoury.signin.usecases.GoogleInitSignInUseCase
import app.flavoury.signin.usecases.GooglePerformSignInUseCase
import app.flavoury.signin.usecases.SkipSignInUseCase

/**
 * Basic functions for dependency injection. Temporary, until Dagger is added.
 */

internal fun provideSignInViewModelFactory(context: Context): SignInViewModelFactory {
    val repository = provideSignInRepository(context)
    return SignInViewModelFactory(
        GoogleInitSignInUseCase(repository),
        GooglePerformSignInUseCase(repository),
        SkipSignInUseCase(repository)
    )
}

internal fun provideSignInRepository(context: Context): SignInRepository<Intent> {
    return SignInRepository(provideGoogleSignInDataSource(context), provideLocalDataSource(context))
}

internal fun provideGoogleSignInDataSource(context: Context): GoogleSignInDataSource<Intent> {
    return GoogleFirebaseSignInDataSource(context)
}

internal fun provideLocalDataSource(context: Context): LocalDataSource = PreferenceDataSource(context)