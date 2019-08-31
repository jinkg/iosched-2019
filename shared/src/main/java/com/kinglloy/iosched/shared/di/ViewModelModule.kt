package com.kinglloy.iosched.shared.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Yalin on 2019-08-31
 */

@Module
abstract class ViewModelModule {
    @Binds
    internal abstract fun bindViewModelFactory(factory: IOSchedViewModelFactory):
            ViewModelProvider.Factory
}