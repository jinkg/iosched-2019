package com.kinglloy.iosched.ui.schedule

import com.kinglloy.iosched.shared.di.FragmentScoped
import com.kinglloy.iosched.ui.schedule.fiters.ScheduleFilterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Yalin on 2019-09-03
 */
@Module
internal abstract class ScheduleModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeScheduleModule(): ScheduleFragment

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeScheduleFilterFragment(): ScheduleFilterFragment
}