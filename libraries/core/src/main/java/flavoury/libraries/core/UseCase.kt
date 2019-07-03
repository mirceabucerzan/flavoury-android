package flavoury.libraries.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import flavoury.libraries.core.UseCase.OperationType
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * Abstract class representing an action that the user can trigger.
 *
 * Execution occurs based on the specified [operationType], in any of the following contexts:
 * - main thread for [OperationType.Simple] operations
 * - large background thread pool for [OperationType.IO] operations
 * - background thread pool with the same # of threads as CPU cores for [OperationType.Computation]
 *
 * Input consists of parameters passed to invoke() and output is of type (Mutable)LiveData<Result<R>>.
 *
 * Supports operation cancellation via a call to [cancel].
 */
abstract class UseCase<in P, R> : CoroutineScope {

    /**
     * Sealed class hierarchy for the different operation types that can be executed by a [UseCase].
     */
    sealed class OperationType {
        /**
         * Defines the thread used by the [UseCase]'s coroutine.
         */
        abstract val dispatcher: CoroutineDispatcher

        /**
         * Represents a synchronous operation which will be run on the main thread.
         */
        class Simple : OperationType() {
            override val dispatcher: CoroutineDispatcher = Dispatchers.Main
        }

        /**
         * Represents an I/O operation which will be run on a dedicated background thread.
         */
        class IO : OperationType() {
            override val dispatcher: CoroutineDispatcher = Dispatchers.IO
        }

        /**
         * Represents a CPU-intensive operation which will be run on a dedicated background thread.
         */
        class Computation : OperationType() {
            override val dispatcher: CoroutineDispatcher = Dispatchers.Default
        }
    }

    /**
     * Override this to specify the [OperationType] of the [UseCase].
     */
    protected abstract val operationType: OperationType

    override val coroutineContext: CoroutineContext
        get() = operationType.dispatcher + coroutineJob

    private val coroutineJob = Job()

    /**
     * Executes the [UseCase] and places the [Result] in a [MutableLiveData].
     *
     * @param parameters The input parameters to run the use case with.
     * @param observableResult The [MutableLiveData] where the [Result] is posted to.
     */
    operator fun invoke(parameters: P, observableResult: MutableLiveData<Result<R>>) {
        observableResult.value = Result.Loading
        launch {
            try {
                execute(parameters).let { result ->
                    observableResult.postValue(Result.Success(result))
                }
            } catch (e: Exception) {
                Timber.e(e)
                observableResult.postValue(Result.Error(e))
            }
        }
    }

    /**
     * Executes the [UseCase] and returns the [Result] in a new [LiveData].
     *
     * @param parameters The input parameters to run the use case with.
     * @return An observable [LiveData] with a [Result].
     */
    operator fun invoke(parameters: P): LiveData<Result<R>> {
        val liveData = MutableLiveData<Result<R>>()
        this(parameters, liveData)
        return liveData
    }

    /**
     * Cancels the [UseCase] immediately.
     */
    fun cancel() {
        coroutineJob.cancel()
        Timber.d("Cancelled use case (coroutine job)")
    }

    /**
     * Override this to specify the action of the [UseCase].
     *
     * @param parameters The input parameters to run the use case with.
     * @return The raw result of the [UseCase], which will be wrapped in a [Result.Success] if successful.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(parameters: P): R

}

/**
 * Sealed class hierarchy representing a [UseCase] result.
 */
sealed class Result<out R> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    object Loading : Result<Nothing>()

}