package com.kinglloy.iosched.shared.domain.prefs

import com.kinglloy.iosched.shared.data.prefs.PreferenceStorage
import com.kinglloy.iosched.shared.domain.UseCase
import javax.inject.Inject

/**
 * Yalin on 2019-08-31
 */
open class OnboardingCompletedUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Unit, Boolean>() {
    override fun execute(parameters: Unit) = preferenceStorage.onboardingCompleted
}