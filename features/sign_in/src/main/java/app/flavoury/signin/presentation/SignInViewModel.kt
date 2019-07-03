package app.flavoury.signin.presentation

import android.content.Intent
import androidx.lifecycle.*
import app.flavoury.signin.usecases.GoogleInitSignInUseCase
import app.flavoury.signin.usecases.GooglePerformSignInUseCase
import app.flavoury.signin.usecases.SkipSignInUseCase
import flavoury.libraries.core.Actions
import flavoury.libraries.core.CoreLog
import flavoury.libraries.core.Result
import flavoury.libraries.core.UniqueEvent
import flavoury.libraries.core.domain.User

/**
 * [ViewModel] which triggers sign in initialization and execution. It also holds observable navigation events.
 */
internal class SignInViewModel(
    private val googleInitSignInUseCase: GoogleInitSignInUseCase<Intent>,
    private val googlePerformSignInUseCase: GooglePerformSignInUseCase<Intent>,
    private val skipSignInUseCase: SkipSignInUseCase<Intent>
) : ViewModel() {

    companion object {
        private const val FLAGS_NEW_TASK: Int = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    private val googleInitIntent = MutableLiveData<Result<Intent>>()
    private val user = MutableLiveData<Result<User>>()
    private val _navigateEvent = MediatorLiveData<UniqueEvent<Intent?>>()
    private val _errorEvent = MutableLiveData<UniqueEvent<Unit>>()

    val navigateForResultEvent: LiveData<UniqueEvent<Intent?>> = Transformations.map(googleInitIntent) { result ->
        when (result) {
            is Result.Loading -> UniqueEvent(null)
            is Result.Success -> UniqueEvent(result.data)
            is Result.Error -> {
                _errorEvent.value = UniqueEvent(Unit)
                UniqueEvent(null)
            }
        }
    }
    val navigateEvent: LiveData<UniqueEvent<Intent?>>
        get() = _navigateEvent
    val errorEvent: LiveData<UniqueEvent<Unit>>
        get() = _errorEvent

    init {
        _navigateEvent.addSource(user) { result ->
            when (result) {
                is Result.Success -> {
                    CoreLog.d("Login success, user = ${user.value}")
                    _navigateEvent.value = UniqueEvent(
                        Actions.openOnboardingIntent(
                            result.data,
                            FLAGS_NEW_TASK
                        )
                    )
                }
                is Result.Error -> {
                    _errorEvent.value = UniqueEvent(Unit)
                }
            }
        }
    }

    fun signInWithGoogle() {
        googleInitSignInUseCase(Unit, googleInitIntent)
    }

    fun skipSignIn() {
        skipSignInUseCase(Unit)
        // TODO Navigate to next feature
        _navigateEvent.value = UniqueEvent(null)
    }

    fun navigationResultReceived(result: Intent?) {
        if (result == null) {
            _errorEvent.value = UniqueEvent(Unit)
        } else {
            googlePerformSignInUseCase(result, user)
        }
    }

    override fun onCleared() {
        googleInitSignInUseCase.cancel()
        googlePerformSignInUseCase.cancel()
        super.onCleared()
    }

}

internal class SignInViewModelFactory(
    private val googleInitSignInUseCase: GoogleInitSignInUseCase<Intent>,
    private val googlePerformSignInUseCase: GooglePerformSignInUseCase<Intent>,
    private val skipSignInUseCase: SkipSignInUseCase<Intent>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(googleInitSignInUseCase, googlePerformSignInUseCase, skipSignInUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")
    }

}