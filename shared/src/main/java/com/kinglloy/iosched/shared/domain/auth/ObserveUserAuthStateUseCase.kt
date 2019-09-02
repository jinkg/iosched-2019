package com.kinglloy.iosched.shared.domain.auth

import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfo
import com.kinglloy.iosched.shared.domain.MediatorUseCase
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A [MediatorUseCase] that observes two data sources to generate an [AuthenticatedUserInfo]
 * that includes whether the user is registered (is an attendee).
 *
 * [AuthStateUserDataSource] provides general user information, like user IDs, while
 * [RegisteredUserDataSource] observes a different data source to provide a flag indicating
 * whether the user is registered.
 */
@Singleton
open class ObserveUserAuthStateUseCase @Inject constructor(

) : MediatorUseCase<Any, AuthenticatedUserInfo>() {
    override fun execute(parameters: Any) {

    }
}