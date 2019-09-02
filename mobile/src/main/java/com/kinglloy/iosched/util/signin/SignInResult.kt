package com.kinglloy.iosched.util.signin

import com.firebase.ui.auth.FirebaseUiException

/**
 * Yalin on 2019-09-02
 */

sealed class SignInResult

object SignInSuccess : SignInResult()
data class SignInFailed(val error: FirebaseUiException?) : SignInResult()