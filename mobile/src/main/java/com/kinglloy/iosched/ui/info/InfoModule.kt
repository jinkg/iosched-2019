package com.kinglloy.iosched.ui.info

import com.kinglloy.iosched.shared.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Yalin on 2019-09-03
 */
@Module
internal abstract class InfoModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeInfoFragment(): InfoFragment
}