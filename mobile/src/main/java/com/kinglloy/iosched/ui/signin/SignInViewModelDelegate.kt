package com.kinglloy.iosched.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    fun observeSignedInUser(): LiveData<Boolean>
}

internal class FirebaseSignInViewModelDelegate @Inject constructor() : SignInViewModelDelegate {
    private val _isSignedIn: LiveData<Boolean>

    init {
        _isSignedIn = MutableLiveData(false)
    }

    override fun observeSignedInUser(): LiveData<Boolean> {
        return _isSignedIn
    }
}