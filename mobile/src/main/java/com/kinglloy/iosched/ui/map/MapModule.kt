package com.kinglloy.iosched.ui.map

import com.kinglloy.iosched.shared.di.FragmentScoped
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Yalin on 2019-09-03
 */
@Module
internal abstract class MapModule {

    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeMapFragment(): MapFragment
}