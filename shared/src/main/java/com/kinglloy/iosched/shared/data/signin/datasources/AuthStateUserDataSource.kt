package com.kinglloy.iosched.shared.data.signin.datasources

import androidx.lifecycle.LiveData
import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfoBasic
import com.kinglloy.iosched.shared.result.Result

/**
 * Listens to an Authentication state data source that emits updates on the current user.
 *
 * @see FirebaseAuthStateUserDataSource
 */
interface AuthStateUserDataSource {
    /**
     * Listens to changes in the authentication-related user info.
     */
    fun startListening()

    /**
     * Returns an observable of the [AuthenticatedUserInfoBasic].
     */
    fun getBasicUserInfo(): LiveData<Result<AuthenticatedUserInfoBasic?>>

    /**
     * Call this method to clear listeners to avoid leaks.
     */
    fun clearListener()
}