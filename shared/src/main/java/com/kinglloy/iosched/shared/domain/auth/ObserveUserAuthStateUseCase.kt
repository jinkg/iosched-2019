package com.kinglloy.iosched.shared.domain.auth

import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfo
import com.kinglloy.iosched.shared.data.signin.FirebaseRegisterdUserInfo
import com.kinglloy.iosched.shared.data.signin.datasources.AuthStateUserDataSource
import com.kinglloy.iosched.shared.domain.MediatorUseCase
import com.kinglloy.iosched.shared.result.Result
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A [MediatorUseCase] that observes two data sources to generate an [AuthenticatedUserInfo]
 * that includes whether the user is registered (is an attendee).
 *
 * [AuthStateUserDataSource] provides general user information, like user IDs, while
 * [RegisteredUserDataSource] observes a different data source to provide a flag indicating
 * whether the user is registered.
 */
@Singleton
open class ObserveUserAuthStateUseCase @Inject constructor(
    private val authStateUserDataSource: AuthStateUserDataSource
) : MediatorUseCase<Any, AuthenticatedUserInfo>() {

    private val currentFirebaseUserObservable = authStateUserDataSource.getBasicUserInfo()

    init {
        // If the Firebase user changes, query firestore to figure out if they're registered.
        result.addSource(currentFirebaseUserObservable) {
            val userResult = currentFirebaseUserObservable.value

            // Start observing the user in Firestore to fetch the `registered` flag
            (userResult as? Result.Success)?.data?.getUid()?.let {

            }
            // Sign out
            if (userResult is Result.Success && userResult.data?.isSignedIn() == false) {
                updateUserObservable()
            }
            if (userResult is Result.Error) {
                result.postValue(Result.Error(Exception("FirebaseAuth error")))
            }
        }
    }

    override fun execute(parameters: Any) {
        // Start listening to the [AuthStateUserDataSource] for changes in auth state.
        authStateUserDataSource.startListening()
    }

    private fun updateUserObservable() {
        val currentFirebaseUser = currentFirebaseUserObservable.value

        if (currentFirebaseUser is Result.Success) {
            result.postValue(
                Result.Success(
                    FirebaseRegisterdUserInfo(currentFirebaseUser.data, true)
                )
            )
        } else {
            Timber.e("There was a registration error.")
            result.postValue(Result.Error(Exception("Registration error")))
        }
    }
}