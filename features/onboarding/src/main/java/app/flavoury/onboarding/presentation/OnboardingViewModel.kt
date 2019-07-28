package app.flavoury.onboarding.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import app.flavoury.onboarding.usecases.FlowStep
import app.flavoury.onboarding.usecases.OnboardingListItem
import app.flavoury.onboarding.usecases.OnboardingUseCase
import flavoury.libraries.core.CoreLog
import flavoury.libraries.core.Result
import flavoury.libraries.core.UniqueEvent
import flavoury.libraries.core.domain.User

internal class OnboardingViewModelFactory(
    private val onboardingUseCase: OnboardingUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            return OnboardingViewModel(onboardingUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")
    }

}

/**
 * ViewModel which holds and manages the UI data for the Onboarding feature.
 */
internal class OnboardingViewModel(
    private val onboardingUseCase: OnboardingUseCase
) : ViewModel() {

    private lateinit var user: User
    private var flowSteps: List<FlowStep> = emptyList()
    private var currentStepIndex = -1

    private val _navDirections = MediatorLiveData<UniqueEvent<NavDirections?>>()
    val navDirections: LiveData<UniqueEvent<NavDirections?>>
        get() = _navDirections

    private val _error = MediatorLiveData<UniqueEvent<Unit>>()
    val error: LiveData<UniqueEvent<Unit>>
        get() = _error

    /** UI model for the Diet preference view */
    private val _dietListItems = MediatorLiveData<List<OnboardingListItem>>()
    val dietListItems: LiveData<List<OnboardingListItem>>
        get() = _dietListItems

    private val _intoleranceListItems = MediatorLiveData<List<OnboardingListItem>>()
    val intoleranceListItems: LiveData<List<OnboardingListItem>>
        get() = _intoleranceListItems

    override fun onCleared() {
        onboardingUseCase.cancel()
        super.onCleared()
    }

    fun start(loggedInUser: User) {
        user = loggedInUser
        val onboardingModel = onboardingUseCase(user)

        _navDirections.addSource(onboardingModel) { result ->
            if (result is Result.Success) {
                flowSteps = result.data.flowSteps
                advanceFlow()
            }
        }

        _error.addSource(onboardingModel) { result ->
            if (result is Result.Error) _error.value =
                UniqueEvent(Unit)
        }

        _dietListItems.addSource(onboardingModel) { result ->
            if (result is Result.Success) {
                _dietListItems.value = result.data.dietListItems
            }
        }

        _intoleranceListItems.addSource(onboardingModel) { result ->
            if (result is Result.Success) {
                _intoleranceListItems.value = result.data.intoleranceListItems
            }
        }
    }

    fun advanceFlow() {
        if (++currentStepIndex == flowSteps.size) {
            // TODO Finish onboarding
            CoreLog.d("Onboarding finished.")
        } else if (currentStepIndex < flowSteps.size) {
            _navDirections.value = UniqueEvent(
                when (flowSteps[currentStepIndex]) {
                    is FlowStep.Diet -> LoadingFragmentDirections.actionLoadingToDiet()
                    is FlowStep.Intolerances -> DietFragmentDirections.actionDietToIntolerances()
                    is FlowStep.AllDone -> IntolerancesFragmentDirections.actionIntolerancesToAllDone()
                }
            )
        }

    }

    fun onBackPressed() {
        if (currentStepIndex >= 0) currentStepIndex--
    }

    fun selectDiet(dietItem: OnboardingListItem) {
        // create a new list, holding new instances with the correct selected state
        val resetItems = mutableListOf<OnboardingListItem>()
        // single selection
        _dietListItems.value?.forEach { item ->
            resetItems += item.copy().apply { selected = item == dietItem }
        }
        _dietListItems.value = resetItems
    }

    fun selectIntolerance(intoleranceItem: OnboardingListItem) {
        val resetItems = mutableListOf<OnboardingListItem>()
        // multiple selection
        _intoleranceListItems.value?.forEach { item ->
            resetItems += item.copy().apply { if (item == intoleranceItem) selected = !selected }
        }
        _intoleranceListItems.value = resetItems
    }
}