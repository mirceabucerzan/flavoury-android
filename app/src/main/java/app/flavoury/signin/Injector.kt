package app.flavoury.signin

import android.content.Context
import android.content.Intent
import app.flavoury.signin.datasource.GoogleSignInDataSource
import app.flavoury.signin.datasource.SignInRepository
import app.flavoury.signin.framework.GoogleFirebaseSignInDataSource
import app.flavoury.signin.presentation.SignInViewModelFactory
import app.flavoury.signin.usecases.GoogleInitSignInUseCase
import app.flavoury.signin.usecases.GooglePerformSignInUseCase

/**
 * Basic functions for dependency injection. Temporary, until Dagger is added.
 */

fun provideSignInViewModelFactory(context: Context): SignInViewModelFactory {
    val repository = SignInRepository(provideGoogleSignInDataSource(context))
    return SignInViewModelFactory(
        GoogleInitSignInUseCase(repository),
        GooglePerformSignInUseCase(repository)
    )
}

fun provideGoogleSignInDataSource(context: Context): GoogleSignInDataSource<Intent> {
    return GoogleFirebaseSignInDataSource(context)
}