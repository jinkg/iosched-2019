package com.kinglloy.iosched.ui.onboarding

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kinglloy.iosched.androidtest.util.LiveDataTestUtil
import com.kinglloy.iosched.shared.data.prefs.PreferenceStorage
import com.kinglloy.iosched.shared.domain.prefs.OnboardingCompleteActionUseCase
import com.kinglloy.iosched.test.util.SyncTaskExecutorRule
import com.kinglloy.iosched.test.util.fakes.FakeSignInViewModelDelegate
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

/**
 * Unit tests for the [OnboardingViewModel].
 */
class OnboardingViewModelTest {
    // Executes tasks in the Architecture Components in the same thread
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    // Executes tasks in a synchronous [TaskScheduler]
    @get:Rule
    var syncTaskExecutorRule = SyncTaskExecutorRule()

    @Test
    fun onGetStartedClicked_updatesPrefs() {
        val prefs = mock<PreferenceStorage>()
        val onboardingCompleteActionUseCase = OnboardingCompleteActionUseCase(prefs)
        val signInDelegate = FakeSignInViewModelDelegate()
        val viewModel = OnboardingViewModel(onboardingCompleteActionUseCase, signInDelegate)

        // When getStarted is called
        viewModel.getStartedClick()

        // Then verify that local storage was updated
        verify(prefs).onboardingCompleted = true

        // And that the navigation event was fired
        val navigateEvent = LiveDataTestUtil.getValue(viewModel.navigateToMainActivity)
        assertNotNull(navigateEvent?.getContentIfNotHandled())
    }

    @Test
    fun onSignInClicked() {
        val prefs = mock<PreferenceStorage>()
        val onboardingCompleteActionUseCase = OnboardingCompleteActionUseCase(prefs)
        val signInDelegate = FakeSignInViewModelDelegate()
        val viewModel = OnboardingViewModel(onboardingCompleteActionUseCase, signInDelegate)

        viewModel.onSignInClicked()

        val navigateEvent = LiveDataTestUtil.getValue(viewModel.navigateToSignInDialogAction)
        assertNotNull(navigateEvent?.getContentIfNotHandled())
    }
}