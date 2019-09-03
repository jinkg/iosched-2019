package com.kinglloy.iosched.ui.signin

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfo
import com.kinglloy.iosched.shared.domain.auth.ObserveUserAuthStateUseCase
import com.kinglloy.iosched.shared.domain.prefs.NotificationsPrefIsShownUseCase
import com.kinglloy.iosched.shared.result.Event
import com.kinglloy.iosched.shared.result.Result
import com.kinglloy.iosched.shared.result.Result.Success
import com.kinglloy.iosched.shared.util.map
import javax.inject.Inject

enum class SignInEvent {
    RequestSignIn, RequestSignOut
}

/**
 * Interface to implement sign-in functionality in a ViewModel.
 *
 * You can inject a implementation of this via Dagger2, then use the implementation as an interface
 * delegate to add sign in functionality without writing any code
 *
 * Example usage
 *
 * ```
 * class MyViewModel @Inject constructor(
 *     signInViewModelComponent: SignInViewModelDelegate
 * ) : ViewModel(), SignInViewModelDelegate by signInViewModelComponent {
 * ```
 */
interface SignInViewModelDelegate {
    /**
     * Live updated value of the current firebase user
     */
    val currentUserInfo: LiveData<AuthenticatedUserInfo?>

    /**
     * Live updated value of the current firebase users image url
     */
    val currentUserImageUri: LiveData<Uri?>

    /**
     * Emits Events when a sign-in event should be attempted
     */
    val performSignInEvent: MutableLiveData<Event<SignInEvent>>

    /**
     * Emits an non-null Event when the dialog to ask the user notifications preference should be
     * shown.
     */
    val shouldShowNotificationsPrefAction: LiveData<Event<Boolean>>

    /**
     * Emit an Event on performSignInEvent to request sign-in
     */
    fun emitSignInRequest()

    /**
     * Emit an Event on performSignInEvent to request sign-out
     */
    fun emitSignOutRequest()

    fun observeSignedInUser(): LiveData<Boolean>

    fun isSignedIn(): Boolean

    fun isRegistered(): Boolean

    /**
     * Returns the current user ID or null if not available.
     */
    fun getUserId(): String?
}

internal class FirebaseSignInViewModelDelegate @Inject constructor(
    observeUserAuthStateUseCase: ObserveUserAuthStateUseCase,
    private val notificationsPrefIsShownUseCase: NotificationsPrefIsShownUseCase
) : SignInViewModelDelegate {

    override val performSignInEvent = MutableLiveData<Event<SignInEvent>>()
    override val currentUserInfo: LiveData<AuthenticatedUserInfo?>
    override val currentUserImageUri: LiveData<Uri?>
    override val shouldShowNotificationsPrefAction = MediatorLiveData<Event<Boolean>>()

    private val _isSignedIn: LiveData<Boolean>

    private val notificationsPrefIsShown = MutableLiveData<Result<Boolean>>()

    init {
        currentUserInfo = observeUserAuthStateUseCase.observe().map { result ->
            (result as? Success)?.data
        }

        currentUserImageUri = currentUserInfo.map { user ->
            user?.getPhotoUrl()
        }

        _isSignedIn = currentUserInfo.map { isSignedIn() }

        observeUserAuthStateUseCase.execute(Any())

        shouldShowNotificationsPrefAction.addSource(notificationsPrefIsShown) {
            showNotificationPref()
        }
        shouldShowNotificationsPrefAction.addSource(_isSignedIn) {
            // Refresh the preferences
            notificationsPrefIsShown.value = null
            notificationsPrefIsShownUseCase(Unit, notificationsPrefIsShown)
        }
    }

    private fun showNotificationPref() {
        val result = (notificationsPrefIsShown.value as? Success)?.data == false && isSignedIn()
        // Show the notification if the preference is not set and the event hasn't been handled yet.
        if (result && (shouldShowNotificationsPrefAction.value?.hasBeenHandled == false)) {
            shouldShowNotificationsPrefAction.value = Event(true)
        }
    }

    /**
     * Emit an Event on performSignInEvent to request sign-in
     */
    override fun emitSignInRequest() {
        // Refresh the notificationsPrefIsShown because it's used to indicate if the
        // notifications preference dialog should be shown
        notificationsPrefIsShownUseCase(Unit, notificationsPrefIsShown)

        performSignInEvent.postValue(Event(SignInEvent.RequestSignIn))
    }

    /**
     * Emit an Event on performSignInEvent to request sign-out
     */
    override fun emitSignOutRequest() {
        performSignInEvent.postValue(Event(SignInEvent.RequestSignOut))
    }

    override fun observeSignedInUser(): LiveData<Boolean> {
        return _isSignedIn
    }

    override fun isSignedIn(): Boolean {
        return currentUserInfo.value?.isSignedIn() == true
    }

    override fun isRegistered(): Boolean {
        return currentUserInfo.value?.isRegistered() == true
    }

    override fun getUserId(): String? {
        return currentUserInfo.value?.getUid()
    }
}