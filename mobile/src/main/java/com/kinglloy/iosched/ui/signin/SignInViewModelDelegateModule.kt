package com.kinglloy.iosched.ui.signin

import com.kinglloy.iosched.shared.domain.auth.ObserveUserAuthStateUseCase
import com.kinglloy.iosched.shared.domain.prefs.NotificationsPrefIsShownUseCase
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
    fun provideSignInViewModelDelegate(
        dataSource: ObserveUserAuthStateUseCase,
        notificationsPrefIsShownUseCase: NotificationsPrefIsShownUseCase
    ): SignInViewModelDelegate {
        return FirebaseSignInViewModelDelegate(dataSource, notificationsPrefIsShownUseCase)
    }
}