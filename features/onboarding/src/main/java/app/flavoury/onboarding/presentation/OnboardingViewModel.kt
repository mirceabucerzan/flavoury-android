package app.flavoury.onboarding.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.flavoury.onboarding.domain.Step
import app.flavoury.onboarding.usecases.OnboardingFlowUseCase
import flavoury.libraries.core.Result
import flavoury.libraries.core.UniqueEvent
import flavoury.libraries.core.domain.User

internal class OnboardingViewModelFactory(
    private val user: User,
    private val onboardingFlowUseCase: OnboardingFlowUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
            return OnboardingViewModel(user, onboardingFlowUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")
    }

}

/**
 * ViewModel which holds and manages the UI data for the Onboarding feature.
 */
internal class OnboardingViewModel(
    user: User,
    private val onboardingFlowUseCase: OnboardingFlowUseCase
) : ViewModel() {

    private val onboardingFlow = onboardingFlowUseCase(user)
    private var steps: List<Step>? = null

    private val _flowStep = MediatorLiveData<FlowStep>()
    /** The current step in the onboarding flow */
    val flowStep: LiveData<FlowStep>
        get() = _flowStep

    private val _flowError = MediatorLiveData<UniqueEvent<Unit>>()
    val flowError: LiveData<UniqueEvent<Unit>>
        get() = _flowError

    /** UI model for the Diet preference view */
    private val _dietListItems = MediatorLiveData<List<DietListItem>>()
    val dietListItems: LiveData<List<DietListItem>>
        get() = _dietListItems

    init {
        _flowStep.addSource(onboardingFlow) { result ->
            if (result is Result.Success) {
                steps = result.data.steps
                steps?.apply { if (isNotEmpty()) _flowStep.value = first().toFlowStep() }
            }
        }
        _flowError.addSource(onboardingFlow) { result ->
            if (result is Result.Error) _flowError.value =
                UniqueEvent(Unit)
        }

        _dietListItems.addSource(onboardingFlow) { result ->
            if (result is Result.Success) {
                val dietPreference = result.data.steps.find { it is Step.DietPreference }
                (dietPreference as? Step.DietPreference)?.let { _dietListItems.value = it.toDietListItems() }
            }
        }
    }

    override fun onCleared() {
        onboardingFlowUseCase.cancel()
        super.onCleared()
    }

    fun selectDiet(dietItem: DietListItem) {
        // create a new list, holding new instances with the correct selected state
        val resetDietItems = mutableListOf<DietListItem>()
        _dietListItems.value?.forEach { item ->
            resetDietItems += item.copy().apply { selected = item == dietItem }
        }
        _dietListItems.value = resetDietItems
    }
}

/**
 * Flow step Ui model.
 */
internal sealed class FlowStep {
    object Diet : FlowStep()
}

internal fun Step.toFlowStep(): FlowStep {
    return when (this) {
        is Step.DietPreference -> FlowStep.Diet
    }
}