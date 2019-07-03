package app.flavoury.onboarding

import app.flavoury.onboarding.datasource.OnboardingRepository
import app.flavoury.onboarding.datasource.UserDataSource
import app.flavoury.onboarding.framework.FakeUserDataSource
import app.flavoury.onboarding.presentation.OnboardingViewModelFactory
import app.flavoury.onboarding.usecases.OnboardingFlowUseCase
import flavoury.libraries.core.domain.User

/**
 * Basic functions for dependency injection. Temporary, until Dagger is added.
 */

internal fun provideOnboardingViewModelFactory(user: User): OnboardingViewModelFactory {
    return OnboardingViewModelFactory(user, provideOnboardingFlowUseCase())
}

internal fun provideOnboardingFlowUseCase() = OnboardingFlowUseCase(provideOnboardingRepository())

internal fun provideOnboardingRepository() = OnboardingRepository(provideUserDataSource())

internal fun provideUserDataSource(): UserDataSource = FakeUserDataSource()