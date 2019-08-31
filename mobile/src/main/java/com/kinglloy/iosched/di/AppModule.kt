package com.kinglloy.iosched.di

import android.content.Context
import com.kinglloy.iosched.MainApplication
import com.kinglloy.iosched.shared.data.prefs.PreferenceStorage
import com.kinglloy.iosched.shared.data.prefs.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Yalin on 2019-08-31
 */

@Module
class AppModule {
    @Provides
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun providePreferenceStorage(context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)
}