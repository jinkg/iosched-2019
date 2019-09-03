package com.kinglloy.iosched.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kinglloy.iosched.shared.data.signin.datasources.AuthStateUserDataSource
import com.kinglloy.iosched.shared.data.signin.datasources.FirebaseAuthStateUserDataSource
import com.kinglloy.iosched.shared.data.signin.datasources.FirestoreRegisteredUserDataSource
import com.kinglloy.iosched.shared.data.signin.datasources.RegisteredUserDataSource
import com.kinglloy.iosched.shared.fcm.FcmTokenUpdater
import com.kinglloy.iosched.util.signin.FirebaseAuthSignInHandler
import com.kinglloy.iosched.util.signin.SignInHandler
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Yalin on 2019-09-02
 */

@Module
internal class SignInModule {
    @Provides
    fun provideSignInHandler(): SignInHandler = FirebaseAuthSignInHandler()

    @Singleton
    @Provides
    fun provideRegisteredUserDataSource(firestore: FirebaseFirestore): RegisteredUserDataSource {
        return FirestoreRegisteredUserDataSource(firestore)
    }

    @Singleton
    @Provides
    fun provideAuthStateUserDataSource(
        firebase: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthStateUserDataSource {
        return FirebaseAuthStateUserDataSource(firebase, FcmTokenUpdater(firestore))
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
}