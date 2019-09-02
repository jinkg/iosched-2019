package com.kinglloy.iosched.shared.data.signin

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

    fun getUid(): String?
}

/**
 * Extra information about the auth and registration state of the user.
 */
interface AuthenticatedUserInfoRegistered {
    fun isRegisterd(): Boolean
}