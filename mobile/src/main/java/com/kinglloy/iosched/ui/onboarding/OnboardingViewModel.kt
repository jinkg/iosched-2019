package com.kinglloy.iosched.ui.onboarding

import androidx.lifecycle.ViewModel
import com.kinglloy.iosched.shared.domain.prefs.OnboardingCompleteActionUseCase
import javax.inject.Inject

/**
 * Yalin on 2019-08-31
 *
 * Records that onboarding has been completed and navigates user onward.
 */
class OnboardingViewModel @Inject constructor(
    private val onboardingCompleteActionUseCase: OnboardingCompleteActionUseCase
) : ViewModel() {
    fun getStartedClick() {
        onboardingCompleteActionUseCase(true)
    }
}