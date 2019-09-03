package com.kinglloy.iosched.ui.speaker

import com.kinglloy.iosched.shared.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Yalin on 2019-09-03
 */
@Module
internal abstract class SpeakerModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSpeakerFragment(): SpeakerFragment
}