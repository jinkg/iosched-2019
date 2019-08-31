package com.kinglloy.iosched.shared.domain.prefs

import com.kinglloy.iosched.shared.data.prefs.PreferenceStorage
import com.kinglloy.iosched.shared.domain.UseCase
import javax.inject.Inject

/**
 * Yalin on 2019-08-31
 *
 * Records whether onboarding has been completed.
 */
open class OnboardingCompleteActionUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Boolean, Unit>() {
    override fun execute(parameters: Boolean) {
        preferenceStorage.onboardingCompleted = parameters
    }
}