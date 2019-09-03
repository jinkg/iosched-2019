package com.kinglloy.iosched.ui.feed

import androidx.lifecycle.ViewModel
import com.kinglloy.iosched.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [FeedFragment] are defined.
 * Yalin on 2019-09-03
 */
@Module
internal abstract class FeedModule {

    /**
     * Generates an [AndroidInjector] for the [FeedFragment] as a Dagger subcomponent of the
     * [MainModule].
     */
    @ContributesAndroidInjector
    internal abstract fun contributeFeedFragment(): FeedFragment

    /**
     * The ViewModels are created by Dagger in a map. Via the @ViewModelKey, we define that we
     * want to get a [FeedViewModel] class.
     */
    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindScheduleFragmentViewModel(viewModel: FeedViewModel): ViewModel
}