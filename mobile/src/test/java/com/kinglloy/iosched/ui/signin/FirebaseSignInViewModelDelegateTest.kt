package com.kinglloy.iosched.ui.signin

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kinglloy.iosched.androidtest.util.LiveDataTestUtil
import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfoBasic
import com.kinglloy.iosched.shared.domain.prefs.NotificationsPrefIsShownUseCase
import com.kinglloy.iosched.test.util.SyncTaskExecutorRule
import com.kinglloy.iosched.ui.schedule.FakeObserveUserAuthStateUserCase
import org.junit.Rule
import org.junit.Test
import com.kinglloy.iosched.shared.result.Result
import com.kinglloy.iosched.test.util.fakes.FakePreferenceStorage
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import junit.framework.TestCase.*

/**
 * Tests for [FirebaseSignInViewModelDelegate]
 * Yalin on 2019-09-03
 */

class FirebaseSignInViewModelDelegateTest {

    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Executes tasks in a synchronous [TaskScheduler]
    @get:Rule
    var syncTaskExecutorRule = SyncTaskExecutorRule()

    @Test
    fun testSignedOut() {
        val subject = FirebaseSignInViewModelDelegate(
            FakeObserveUserAuthStateUserCase(
                user = Result.Success(null),
                isRegistered = Result.Success(false)
            ),
            createaNotificationPrefIsShownUseCase()
        )

        val currentFirebaseUser = LiveDataTestUtil.getValue(subject.currentUserInfo)
        assertEquals(null, currentFirebaseUser?.getUid())
        assertEquals(null, LiveDataTestUtil.getValue(subject.currentUserImageUri))
        assertFalse(subject.isSignedIn())
    }

    @Test
    fun testSignedInRegistered() {
        val user = mock<AuthenticatedUserInfoBasic> {
            on { getUid() }.doReturn("123")
            on { getPhotoUrl() }.doReturn(mock<Uri> {})
            on { isSignedIn() }.doReturn(true)
        }

        val subject = FirebaseSignInViewModelDelegate(
            FakeObserveUserAuthStateUserCase(
                user = Result.Success(user),
                isRegistered = Result.Success(true)
            ),
            createaNotificationPrefIsShownUseCase()
        )

        assertEquals(user.getUid(), LiveDataTestUtil.getValue(subject.currentUserInfo)?.getUid())
        assertEquals(user.getPhotoUrl(), LiveDataTestUtil.getValue(subject.currentUserImageUri))
        assertTrue(subject.isSignedIn())
        assertTrue(subject.isRegistered())
    }

    @Test
    fun testSignedInNotRegistered() {
        val user = mock<AuthenticatedUserInfoBasic> {
            on { getUid() }.doReturn("123")
            on { getPhotoUrl() }.doReturn(mock<Uri> {})
            on { isSignedIn() }.doReturn(true)
        }

        val subject = FirebaseSignInViewModelDelegate(
            FakeObserveUserAuthStateUserCase(
                user = Result.Success(user),
                isRegistered = Result.Success(false)
            ),
            createaNotificationPrefIsShownUseCase()
        )

        assertEquals(user.getUid(), LiveDataTestUtil.getValue(subject.currentUserInfo)?.getUid())
        assertEquals(user.getPhotoUrl(), LiveDataTestUtil.getValue(subject.currentUserImageUri))
        assertTrue(subject.isSignedIn())
        assertFalse(subject.isRegistered())
    }

    @Test
    fun testPostSignIn() {
        val subject = FirebaseSignInViewModelDelegate(
            FakeObserveUserAuthStateUserCase(
                user = Result.Success(null),
                isRegistered = Result.Success(false)
            ),
            createaNotificationPrefIsShownUseCase()
        )

        subject.emitSignInRequest()

        // Check that the emitted event is a sign in request
        assertEquals(
            LiveDataTestUtil.getValue(subject.performSignInEvent)?.peekContent(),
            SignInEvent.RequestSignIn
        )
    }

    @Test
    fun testPostSignOut() {
        val subject = FirebaseSignInViewModelDelegate(
            FakeObserveUserAuthStateUserCase(
                user = Result.Success(null),
                isRegistered = Result.Success(false)
            ),
            createaNotificationPrefIsShownUseCase()
        )
        subject.emitSignOutRequest()

        assertEquals(
            LiveDataTestUtil.getValue(subject.performSignInEvent)?.peekContent(),
            SignInEvent.RequestSignOut
        )
    }

    private fun createaNotificationPrefIsShownUseCase(): NotificationsPrefIsShownUseCase {
        return NotificationsPrefIsShownUseCase(FakePreferenceStorage())
    }
}