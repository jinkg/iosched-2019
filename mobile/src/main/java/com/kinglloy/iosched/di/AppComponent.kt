package com.kinglloy.iosched.di

import com.kinglloy.iosched.MainApplication
import com.kinglloy.iosched.shared.di.ViewModelModule
import com.kinglloy.iosched.ui.signin.SignInViewModelDelegateModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Yalin on 2019-08-31
 */
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBindingModule::class,
        ViewModelModule::class,
        SignInViewModelDelegateModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApplication> {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: MainApplication): AppComponent
    }
}