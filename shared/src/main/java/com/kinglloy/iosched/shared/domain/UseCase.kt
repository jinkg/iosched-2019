package com.kinglloy.iosched.shared.domain

import androidx.lifecycle.MutableLiveData
import com.kinglloy.iosched.shared.domain.internal.DefaultScheduler
import com.kinglloy.iosched.shared.domain.internal.Scheduler
import java.lang.RuntimeException
import com.kinglloy.iosched.shared.result.Result
import java.lang.Exception

/**
 * Executes business logic synchronously or asynchronously using a [Scheduler].
 */
abstract class UseCase<in P, R> {
    protected var taskScheduler: Scheduler = DefaultScheduler

    /**
     * Executes the use case asynchronously and places the [Result] in a MutableLiveData
     *
     * @param parameters the input parameters to run the use case with
     * @param result the MutableLiveData where the result is posted to
     */
    operator fun invoke(parameters: P, result: MutableLiveData<Result<R>>) {
        // result.value = Result.Loading TODO: add data to Loading to avoid glitches
        try {
            taskScheduler.execute {
                try {
                    execute(parameters).let { useCaseResult ->
                        result.postValue(Result.Success(useCaseResult))
                    }
                } catch (e: Exception) {
                    result.postValue(Result.Error(e))
                }
            }
        } catch (e: Exception) {
            result.postValue(Result.Error(e))
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract fun execute(parameters: P): R
}