package com.kinglloy.iosched.di

import com.kinglloy.iosched.shared.di.ActivityScoped
import com.kinglloy.iosched.ui.LaunchModule
import com.kinglloy.iosched.ui.LauncherActivity
import com.kinglloy.iosched.ui.MainActivity
import com.kinglloy.iosched.ui.MainActivityModule
import com.kinglloy.iosched.ui.agenda.AgendaModule
import com.kinglloy.iosched.ui.codelabs.CodelabsModule
import com.kinglloy.iosched.ui.feed.FeedModule
import com.kinglloy.iosched.ui.info.InfoModule
import com.kinglloy.iosched.ui.map.MapModule
import com.kinglloy.iosched.ui.onboarding.OnboardingActivity
import com.kinglloy.iosched.ui.onboarding.OnboardingModule
import com.kinglloy.iosched.ui.schedule.ScheduleModule
import com.kinglloy.iosched.ui.search.SearchModule
import com.kinglloy.iosched.ui.sessiondetail.SessionDetailModule
import com.kinglloy.iosched.ui.settings.SettingsModule
import com.kinglloy.iosched.ui.signin.SignInDialogModule
import com.kinglloy.iosched.ui.speaker.SpeakerModule
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
    @ContributesAndroidInjector(
        modules = [
            OnboardingModule::class,
            SignInDialogModule::class
        ]
    )
    internal abstract fun onboardingActivity(): OnboardingActivity

    @ActivityScoped
    @ContributesAndroidInjector(
        modules = [
            // activity
            MainActivityModule::class,
            // fragments
            AgendaModule::class,
            CodelabsModule::class,
            FeedModule::class,
            InfoModule::class,
            MapModule::class,
            ScheduleModule::class,
            SearchModule::class,
            SessionDetailModule::class,
            SettingsModule::class,
            SpeakerModule::class,
            // other
            SignInDialogModule::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity
}