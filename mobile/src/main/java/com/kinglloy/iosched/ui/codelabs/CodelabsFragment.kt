package com.kinglloy.iosched.ui.codelabs

import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.ui.MainNavigationFragment
import javax.inject.Inject

/**
 * Yalin on 2019-09-03
 */
class CodelabsFragment : MainNavigationFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
}