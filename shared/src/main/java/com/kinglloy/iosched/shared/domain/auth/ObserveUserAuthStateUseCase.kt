package com.kinglloy.iosched.shared.domain.auth

import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfo
import com.kinglloy.iosched.shared.data.signin.FirebaseRegisterdUserInfo
import com.kinglloy.iosched.shared.data.signin.datasources.AuthStateUserDataSource
import com.kinglloy.iosched.shared.data.signin.datasources.RegisteredUserDataSource
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
    registeredUserDataSource: RegisteredUserDataSource,
    private val authStateUserDataSource: AuthStateUserDataSource
) : MediatorUseCase<Any, AuthenticatedUserInfo>() {

    private val currentFirebaseUserObservable = authStateUserDataSource.getBasicUserInfo()

    private val isUserRegisteredObservable = registeredUserDataSource.observeResult()

    init {
        // If the Firebase user changes, query firestore to figure out if they're registered.
        result.addSource(currentFirebaseUserObservable) {
            val userResult = currentFirebaseUserObservable.value

            // Start observing the user in Firestore to fetch the `registered` flag
            (userResult as? Result.Success)?.data?.getUid()?.let {
                registeredUserDataSource.listenToUserChanges(it)
            }
            // Sign out
            if (userResult is Result.Success && userResult.data?.isSignedIn() == false) {
                registeredUserDataSource.setAnonymousValue()
                updateUserObservable()
                unsubscribeFromRegisteredTopic()
            }
            // Error
            if (userResult is Result.Error) {
                result.postValue(Result.Error(Exception("FirebaseAuth error")))
            }
        }
        // If the Firestore information about the user changes, update result.
        result.addSource(isUserRegisteredObservable) {
            // When the flag that indicates if an user is an attendee is fetched,
            // update the user result with it:
            updateUserObservable()
        }
    }

    override fun execute(parameters: Any) {
        // Start listening to the [AuthStateUserDataSource] for changes in auth state.
        authStateUserDataSource.startListening()
    }

    private fun updateUserObservable() {
        val currentFirebaseUser = currentFirebaseUserObservable.value
        val isRegistered = isUserRegisteredObservable.value

        if (currentFirebaseUser is Result.Success) {
            // If the isRegistered value is an error, assign it false. Null means no info yet.
            val isRegisteredValue: Boolean? = (isRegistered as? Result.Success)?.data

            // Whenever there's new user data and the user is an attendee, subscribe to topic:
            if (isRegisteredValue == true && currentFirebaseUser.data?.isSignedIn() == true) {
                subscribeToRegisteredTopic()
            }

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

    private fun subscribeToRegisteredTopic() {

    }

    private fun unsubscribeFromRegisteredTopic() {

    }
}