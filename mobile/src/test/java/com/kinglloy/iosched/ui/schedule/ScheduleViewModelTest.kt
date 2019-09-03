package com.kinglloy.iosched.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinglloy.iosched.shared.data.signin.AuthenticatedUserInfoBasic
import com.kinglloy.iosched.shared.data.signin.datasources.AuthStateUserDataSource
import com.kinglloy.iosched.shared.data.signin.datasources.RegisteredUserDataSource
import com.kinglloy.iosched.shared.domain.auth.ObserveUserAuthStateUseCase
import com.kinglloy.iosched.shared.result.Result

/**
 * Unit tests for the [ScheduleViewModel].
 */

class FakeObserveUserAuthStateUserCase(
    user: Result<AuthenticatedUserInfoBasic?>,
    isRegistered: Result<Boolean?>
) : ObserveUserAuthStateUseCase(
    TestRegisteredUserDataSource(isRegistered),
    TestAuthStateUserDataSource(user)
)

class TestRegisteredUserDataSource(private val isRegistered: Result<Boolean?>) :
    RegisteredUserDataSource {
    override fun listenToUserChanges(userId: String) {
    }

    override fun observeResult(): LiveData<Result<Boolean?>?> {
        return MutableLiveData<Result<Boolean?>?>().apply { value = isRegistered }
    }

    override fun setAnonymousValue() {
    }
}

class TestAuthStateUserDataSource(
    private val user: Result<AuthenticatedUserInfoBasic?>?
) : AuthStateUserDataSource {
    override fun startListening() {
    }

    override fun getBasicUserInfo(): LiveData<Result<AuthenticatedUserInfoBasic?>> =
        MutableLiveData<Result<AuthenticatedUserInfoBasic?>>().apply { value = user }

    override fun clearListener() {
    }
}