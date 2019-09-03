package com.kinglloy.iosched.ui.search

import androidx.lifecycle.ViewModel
import com.kinglloy.iosched.shared.di.FragmentScoped
import com.kinglloy.iosched.shared.di.ViewModelKey
import com.kinglloy.iosched.ui.schedule.ScheduleViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Yalin on 2019-09-03
 */
@Module
internal abstract class SearchModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSearchFragment(): SearchFragment

    @Binds
    @IntoMap
    @ViewModelKey(ScheduleViewModel::class)
    abstract fun bindScheduleViewModel(viewModel: ScheduleViewModel): ViewModel
}