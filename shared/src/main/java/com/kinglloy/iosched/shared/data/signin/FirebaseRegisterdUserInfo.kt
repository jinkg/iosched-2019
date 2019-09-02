package com.kinglloy.iosched.shared.data.signin

import com.google.firebase.auth.FirebaseUser

/**
 * Delegates [AuthenticatedUserInfo] calls to a [FirebaseUser] to be used in production.
 */

class FirebaseRegisterdUserInfo(
    private val basicUserInfo: AuthenticatedUserInfoBasic?,
    private val isRegistered: Boolean?
) : AuthenticatedUserInfo {
    override fun isSignedIn() = basicUserInfo?.isSignedIn() == true

    override fun getUid() = basicUserInfo?.getUid()

    override fun isRegistered() = isRegistered ?: false
}

open class FirebaseUserInfo(
    private val firebaseUser: FirebaseUser?
) : AuthenticatedUserInfoBasic {
    override fun isSignedIn() = firebaseUser != null

    override fun getUid() = firebaseUser?.uid
}