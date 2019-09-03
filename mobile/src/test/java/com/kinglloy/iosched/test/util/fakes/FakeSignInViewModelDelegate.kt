package com.kinglloy.iosched.test.util.fakes

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfo
import com.kinglloy.iosched.shared.result.Event
import com.kinglloy.iosched.ui.signin.SignInEvent
import com.kinglloy.iosched.ui.signin.SignInViewModelDelegate

/**
 * Yalin on 2019-09-03
 */
class FakeSignInViewModelDelegate : SignInViewModelDelegate {

    override val currentUserInfo = MutableLiveData<AuthenticatedUserInfo?>()
    override val currentUserImageUri = MutableLiveData<Uri?>()
    override val performSignInEvent = MutableLiveData<Event<SignInEvent>>()
    override val shouldShowNotificationsPrefAction = MutableLiveData<Event<Boolean>>()

    var injectIsSignedIn = true
    var injectIsRegistered = false
    var signInRequestEmitted = 0
    var signOutRequestEmitted = 0

    override fun emitSignInRequest() {
        signInRequestEmitted++
    }

    override fun emitSignOutRequest() {
        signOutRequestEmitted++
    }

    override fun observeSignedInUser() = MutableLiveData<Boolean>().apply {
        value = injectIsSignedIn
    }

    override fun isSignedIn() = injectIsSignedIn

    override fun isRegistered() = injectIsRegistered

    override fun getUserId() = currentUserInfo.value?.getUid()
}