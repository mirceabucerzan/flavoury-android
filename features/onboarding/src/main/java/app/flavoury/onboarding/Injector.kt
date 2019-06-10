package app.flavoury.onboarding

import app.flavoury.onboarding.datasource.OnboardingRepository
import app.flavoury.onboarding.datasource.UserDataSource
import app.flavoury.onboarding.framework.FakeUserDataSource
import app.flavoury.onboarding.presentation.OnboardingViewModelFactory
import app.flavoury.onboarding.usecases.OnboardingFlowUseCase
import com.mirceabucerzan.core.domain.User

/**
 * Basic functions for dependency injection. Temporary, until Dagger is added.
 */

fun provideOnboardingViewModelFactory(user: User): OnboardingViewModelFactory {
    return OnboardingViewModelFactory(user, provideOnboardingFlowUseCase())
}

fun provideOnboardingFlowUseCase() = OnboardingFlowUseCase(provideOnboardingRepository())

fun provideOnboardingRepository() = OnboardingRepository(provideUserDataSource())

fun provideUserDataSource(): UserDataSource = FakeUserDataSource()