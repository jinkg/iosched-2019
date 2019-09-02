package com.kinglloy.iosched.shared.domain.prefs

import com.kinglloy.iosched.shared.data.prefs.PreferenceStorage
import com.kinglloy.iosched.shared.domain.UseCase
import javax.inject.Inject

/**
 * Yalin on 2019-09-02
 *
 * Returns whether the notifications preference has been shown to the user.
 */
open class NotificationsPrefIsShownUseCase @Inject constructor(
    private val preferenceStorage: PreferenceStorage
) : UseCase<Unit, Boolean>() {
    override fun execute(parameters: Unit): Boolean = preferenceStorage.notificationsPreferenceShown
}
