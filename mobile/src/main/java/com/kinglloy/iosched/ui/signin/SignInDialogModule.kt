package com.kinglloy.iosched.ui.signin

import com.kinglloy.iosched.shared.di.ChildFragmentScoped
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

/**
 * Module that defines the child fragments related to sign in/out.
 */
@Module
internal abstract class SignInDialogModule {
    /**
     * Generates an [AndroidInjector] for the [SignInDialogFragment].
     */
    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSignInDialogFragment(): SignInDialogFragment

    /**
     * Generates an [AndroidInjector] for the [SignOutDialogFragment].
     */
    @ChildFragmentScoped
    @ContributesAndroidInjector
    internal abstract fun contributeSignOutDialogFragment(): SignOutDialogFragment
}