package com.kinglloy.iosched.shared.data.signin

import android.net.Uri
import com.google.firebase.auth.FirebaseUser

/**
 * Delegates [AuthenticatedUserInfo] calls to a [FirebaseUser] to be used in production.
 */

class FirebaseRegisterdUserInfo(
    private val basicUserInfo: AuthenticatedUserInfoBasic?,
    private val isRegistered: Boolean?
) : AuthenticatedUserInfo {
    override fun isSignedIn() = basicUserInfo?.isSignedIn() == true

    override fun isAnonymous(): Boolean? = basicUserInfo?.isAnonymous()

    override fun getUid() = basicUserInfo?.getUid()

    override fun getDisplayName(): String? = basicUserInfo?.getDisplayName()

    override fun getPhotoUrl() = basicUserInfo?.getPhotoUrl()

    override fun isRegistered() = isRegistered ?: false
}

open class FirebaseUserInfo(
    private val firebaseUser: FirebaseUser?
) : AuthenticatedUserInfoBasic {
    override fun isSignedIn() = firebaseUser != null

    override fun isAnonymous(): Boolean? = firebaseUser?.isAnonymous

    override fun getUid() = firebaseUser?.uid

    override fun getDisplayName(): String? = firebaseUser?.displayName

    override fun getPhotoUrl(): Uri? = firebaseUser?.photoUrl
}