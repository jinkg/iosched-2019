package com.kinglloy.iosched.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfo
import com.kinglloy.iosched.shared.domain.auth.ObserveUserAuthStateUseCase
import com.kinglloy.iosched.shared.result.Result
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
    observeUserAuthStateUseCase: ObserveUserAuthStateUseCase
) : SignInViewModelDelegate {

    override val currentUserInfo: LiveData<AuthenticatedUserInfo?>

    private val _isSignedIn: LiveData<Boolean>

    init {
        currentUserInfo = observeUserAuthStateUseCase.observe().map { result ->
            (result as? Result.Success)?.data
        }
        _isSignedIn = MutableLiveData(false)
    }

    /**
     * Emit an Event on performSignInEvent to request sign-in
     */
    override fun emitSignInRequest() {

    }

    /**
     * Emit an Event on performSignInEvent to request sign-out
     */
    override fun emitSignOutRequest() {

    }

    override fun observeSignedInUser(): LiveData<Boolean> {
        return _isSignedIn
    }

    override fun isSignedIn(): Boolean {
        return currentUserInfo.value?.isSignedIn() == true
    }

    override fun isRegistered(): Boolean {
        return currentUserInfo.value?.isRegisterd() == true
    }

    override fun getUserId(): String? {
        return currentUserInfo.value?.getUid()
    }
}