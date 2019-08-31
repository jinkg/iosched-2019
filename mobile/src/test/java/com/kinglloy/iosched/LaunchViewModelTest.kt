package com.kinglloy.iosched

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kinglloy.iosched.androidtest.util.LiveDataTestUtil
import com.kinglloy.iosched.shared.data.prefs.PreferenceStorage
import com.kinglloy.iosched.shared.domain.prefs.OnboardingCompletedUseCase
import com.kinglloy.iosched.test.util.SyncTaskExecutorRule
import com.kinglloy.iosched.ui.LaunchDestination.MAIN_ACTIVITY
import com.kinglloy.iosched.ui.LaunchDestination.ONBOARDING
import com.kinglloy.iosched.ui.LaunchViewModel
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

/**
 * Yalin on 2019-08-31
 */

class LaunchViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var syncTaskExecutorRule = SyncTaskExecutorRule()

    @Test
    fun notCompletedOnboarding_navigatesToOnboarding() {
        val prefs = mock<PreferenceStorage> {
            on { onboardingCompleted }.doReturn(false)
        }
        val onboardingCompletedUseCase = OnboardingCompletedUseCase(prefs)
        val viewModel = LaunchViewModel(onboardingCompletedUseCase)

        val navigateEvent = LiveDataTestUtil.getValue(viewModel.launchDestination)
        assertEquals(ONBOARDING, navigateEvent?.getContentIfNotHandled())
    }

    @Test
    fun hasCompletedOnboarding_navigatesToMainActivity() {
        val prefs = mock<PreferenceStorage> {
            on { onboardingCompleted }.doReturn(true)
        }
        val onboardingCompletedUseCase = OnboardingCompletedUseCase(prefs)
        val viewModel = LaunchViewModel(onboardingCompletedUseCase)

        val navigateEvent = LiveDataTestUtil.getValue(viewModel.launchDestination)
        assertEquals(MAIN_ACTIVITY, navigateEvent?.getContentIfNotHandled())
    }
}