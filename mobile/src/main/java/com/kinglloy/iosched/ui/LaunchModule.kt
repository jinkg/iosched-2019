package com.kinglloy.iosched.ui

import androidx.lifecycle.ViewModel
import com.kinglloy.iosched.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Yalin on 2019-08-31
 */

@Module
internal abstract class LaunchModule {
    @Binds
    @IntoMap
    @ViewModelKey(LaunchViewModel::class)
    internal abstract fun bindLaunchViewModel(viewModel: LaunchViewModel): ViewModel
}