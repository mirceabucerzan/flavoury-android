package app.flavoury.signin.presentation

import android.content.Intent
import androidx.lifecycle.*
import app.flavoury.signin.domain.User
import app.flavoury.signin.usecases.GoogleInitSignInUseCase
import app.flavoury.signin.usecases.GooglePerformSignInUseCase
import com.mirceabucerzan.core.CoreLog
import com.mirceabucerzan.core.Result
import com.mirceabucerzan.core.UniqueEvent

/**
 * [ViewModel] which triggers sign in initialization and execution. It also holds observable navigation events.
 */
class SignInViewModel(
    private val googleInitSignInUseCase: GoogleInitSignInUseCase<Intent>,
    private val googlePerformSignInUseCase: GooglePerformSignInUseCase<Intent>
) : ViewModel() {

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
                    // TODO Navigate to next feature
                    CoreLog.d("Login success, user = ${user.value}")
                    _navigateEvent.value = UniqueEvent(null)
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

class SignInViewModelFactory(
    private val googleInitSignInUseCase: GoogleInitSignInUseCase<Intent>,
    private val googlePerformSignInUseCase: GooglePerformSignInUseCase<Intent>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(googleInitSignInUseCase, googlePerformSignInUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class $modelClass")
    }

}