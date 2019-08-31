package com.kinglloy.iosched.ui.onboarding

import androidx.lifecycle.ViewModel
import com.kinglloy.iosched.shared.di.FragmentScoped
import com.kinglloy.iosched.shared.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

/**
 * Module where classes needed to create the [OnboardingFragment] are defined.
 */
@Module
internal abstract class OnboardingModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeOnboardingFragment(): OnboardingFragment

    @Binds
    @IntoMap
    @ViewModelKey(OnboardingViewModel::class)
    internal abstract fun bindOnboardingViewModel(viewModel: OnboardingViewModel): ViewModel
}