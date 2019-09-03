package com.kinglloy.iosched.shared.data.signin

import android.net.Uri

/**
 * Interface to decouple the user info from Firebase.
 *
 * @see [FirebaseRegisteredUserInfo]
 */
interface AuthenticatedUserInfo : AuthenticatedUserInfoBasic, AuthenticatedUserInfoRegistered

/**
 * Basic user info.
 */
interface AuthenticatedUserInfoBasic {
    fun isSignedIn(): Boolean

    fun isAnonymous(): Boolean?

    fun getUid(): String?

    fun getDisplayName(): String?

    fun getPhotoUrl(): Uri?
}

/**
 * Extra information about the auth and registration state of the user.
 */
interface AuthenticatedUserInfoRegistered {
    fun isRegistered(): Boolean
}