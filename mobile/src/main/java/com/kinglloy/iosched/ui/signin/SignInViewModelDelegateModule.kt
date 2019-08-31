package com.kinglloy.iosched.ui.signin

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Yalin on 2019-08-31
 */
@Module
class SignInViewModelDelegateModule {
    @Singleton
    @Provides
    fun provideSignInViewModelDelegate(): SignInViewModelDelegate {
        return FirebaseSignInViewModelDelegate()
    }
}