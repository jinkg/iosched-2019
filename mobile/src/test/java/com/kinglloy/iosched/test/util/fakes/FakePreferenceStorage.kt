package com.kinglloy.iosched.test.util.fakes

import com.kinglloy.iosched.shared.data.prefs.PreferenceStorage

/**
 * Yalin on 2019-09-03
 */
class FakePreferenceStorage(
    override var onboardingCompleted: Boolean = false,
    override var notificationsPreferenceShown: Boolean = false
) : PreferenceStorage