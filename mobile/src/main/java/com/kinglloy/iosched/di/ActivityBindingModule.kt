package com.kinglloy.iosched.di

import com.kinglloy.iosched.shared.di.ActivityScoped
import com.kinglloy.iosched.ui.LaunchModule
import com.kinglloy.iosched.ui.LauncherActivity
import com.kinglloy.iosched.ui.MainActivity
import com.kinglloy.iosched.ui.onboarding.OnboardingActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Yalin on 2019-08-31
 */
@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [LaunchModule::class])
    internal abstract fun launcherActivity(): LauncherActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun onboardingActivity(): OnboardingActivity

    @ActivityScoped
    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}