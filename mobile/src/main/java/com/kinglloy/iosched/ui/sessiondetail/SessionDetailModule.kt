package com.kinglloy.iosched.ui.sessiondetail

import com.kinglloy.iosched.shared.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Yalin on 2019-09-03
 */

@Module
internal abstract class SessionDetailModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSessionDetailFragment(): SessionDetailFragment
}