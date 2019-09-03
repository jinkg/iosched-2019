package com.kinglloy.iosched.ui.signin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kinglloy.iosched.androidtest.util.LiveDataTestUtil
import com.kinglloy.iosched.test.util.SyncTaskExecutorRule
import com.kinglloy.iosched.test.util.fakes.FakeSignInViewModelDelegate
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

/**
 * Yalin on 2019-09-03
 */

class SignInViewModelTest {
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Executes tasks in a synchronous [TaskScheduler]
    @get:Rule
    var syncTaskExecutorRule = SyncTaskExecutorRule()

    @Test
    fun signedInUser_signsOut() {
        // Given a view model with a signed in user
        val signInViewModelDelegate = FakeSignInViewModelDelegate().apply {
            injectIsSignedIn = true
        }
        val viewModel = SignInViewModel(signInViewModelDelegate)

        // When sign out is requested
        viewModel.onSignOut()

        // Then a sign out request is emitted
        assertEquals(1, signInViewModelDelegate.signOutRequestEmitted)
    }

    @Test
    fun noSignedInUser_signsIn() {
        // Given a view model with a signed out user
        val signInViewModelDelegate = FakeSignInViewModelDelegate().apply {
            injectIsSignedIn = false
        }

        val viewModel = SignInViewModel(signInViewModelDelegate)

        // When sign out is requested
        viewModel.onSignIn()

        // Then a sign out request is emitted
        assertEquals(1, signInViewModelDelegate.signInRequestEmitted)
    }

    @Test
    fun onCancel_dialogDismiss() {
        val viewModel = SignInViewModel(FakeSignInViewModelDelegate())

        viewModel.onCancel()

        val disMissEvent = LiveDataTestUtil.getValue(viewModel.dismissDialogAction)
        assertNotNull(disMissEvent?.getContentIfNotHandled())
    }
}