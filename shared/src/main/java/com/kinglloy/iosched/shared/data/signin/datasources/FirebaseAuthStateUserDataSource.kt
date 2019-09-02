package com.kinglloy.iosched.shared.data.signin.datasources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfoBasic
import com.kinglloy.iosched.shared.data.signin.FirebaseUserInfo
import com.kinglloy.iosched.shared.domain.internal.DefaultScheduler
import com.kinglloy.iosched.shared.fcm.FcmTokenUpdater
import timber.log.Timber
import javax.inject.Inject
import com.kinglloy.iosched.shared.result.Result
import java.lang.Exception

/**
 * An [AuthStateUserDataSource] that listens to changes in [FirebaseAuth].
 *
 * When a [FirebaseUser] is available, it
 *  * Posts it to the user observable
 *  * Fetches the ID token
 *  * Uses the ID token to trigger the registration point
 *  * Stores the FCM ID Token in Firestore
 *  * Posts the user ID to the observable
 *
 * This data source doesn't find if a user is registered or not (is an attendee). Once the
 * registration point is called, the server will generate a field in the user document, which
 * is observed by [RegisteredUserDataSource] in its implementation
 * [FirestoreRegisteredUserDataSource].
 */
class FirebaseAuthStateUserDataSource @Inject constructor(
    val firebase: FirebaseAuth,
    private val tokenUpdater: FcmTokenUpdater
) : AuthStateUserDataSource {

    private val currentFirebaseUserObservable =
        MutableLiveData<Result<AuthenticatedUserInfoBasic?>>()

    private var isAlreadyListening = false

    private var lastUid: String? = null

    private val authStateListener: ((FirebaseAuth) -> Unit) = { auth ->
        DefaultScheduler.execute {
            Timber.d("Received a FirebaseAuth update.")

            // Post the current user for observers
            currentFirebaseUserObservable.postValue(
                Result.Success(
                    FirebaseUserInfo(auth.currentUser)
                )
            )


            auth.currentUser?.let { currentUser ->
                // Get the ID token (force refresh)
                val tokenTask = currentUser.getIdToken(true)
                try {
                    // Do this synchronously
                    val await: GetTokenResult = Tasks.await(tokenTask)
                    await.token?.let {
                        // Call registration point to generate a result in Firestore
                        Timber.d("User authenticated, hitting registration endpoint")
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                    return@let
                }

                // Save the FCM ID token in firestore
                tokenUpdater.updateTokenForUser(currentUser.uid)
            }
        }
        if (auth.currentUser == null) {

        }
        auth.currentUser?.let {
            if (lastUid != auth.uid) {

            }
        }

        // Save the last UID to prevent setting too many alarms.
        lastUid = auth.uid
    }

    override fun startListening() {
        if (!isAlreadyListening) {
            firebase.addAuthStateListener(authStateListener)
            isAlreadyListening = true
        }
    }

    override fun getBasicUserInfo(): LiveData<Result<AuthenticatedUserInfoBasic?>> {
        return currentFirebaseUserObservable
    }

    override fun clearListener() {
        firebase.removeAuthStateListener(authStateListener)
    }

}