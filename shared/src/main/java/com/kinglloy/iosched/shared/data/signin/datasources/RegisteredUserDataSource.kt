package com.kinglloy.iosched.shared.data.signin.datasources

import androidx.lifecycle.LiveData
import com.kinglloy.iosched.shared.result.Result

/**
 * A data source that listens to changes in the user data related to event
 * registration.
 */

interface RegisteredUserDataSource {
    /**
     * Listens to changes in the user document in Firestore. A Change in the "registered" field
     * will emit a new user.
     */
    fun listenToUserChanges(userId: String)

    /**
     * Returns the holder of the result of listening to the data source.
     */
    fun observeResult(): LiveData<Result<Boolean?>?>

    /**
     * Clear listeners and set the result of the observable to false when the user is not signed in.
     */
    fun setAnonymousValue()
}