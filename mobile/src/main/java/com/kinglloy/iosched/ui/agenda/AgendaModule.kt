package com.kinglloy.iosched.ui.agenda

import com.kinglloy.iosched.shared.di.ChildFragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Yalin on 2019-09-03
 */
@Module
internal abstract class AgendaModule {

    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeScheduleAgendaFragment(): AgendaFragment
}