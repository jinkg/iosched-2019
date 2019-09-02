package com.kinglloy.iosched.di

import com.kinglloy.iosched.util.signin.FirebaseAuthSignInHandler
import com.kinglloy.iosched.util.signin.SignInHandler
import dagger.Module
import dagger.Provides

/**
 * Yalin on 2019-09-02
 */

@Module
internal class SignInModule {
    @Provides
    fun provideSignInHandler(): SignInHandler = FirebaseAuthSignInHandler()
}