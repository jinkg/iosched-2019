package com.kinglloy.iosched.shared.data.signin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kinglloy.iosched.androidtest.util.LiveDataTestUtil
import com.kinglloy.iosched.shared.data.signin.datasources.AuthStateUserDataSource
import com.kinglloy.iosched.shared.data.signin.datasources.RegisteredUserDataSource
import com.kinglloy.iosched.shared.domain.auth.ObserveUserAuthStateUseCase
import com.kinglloy.iosched.shared.result.Result
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual.equalTo
import org.hamcrest.core.IsInstanceOf.instanceOf
import org.junit.Rule
import org.junit.Test

/**
 * Tests for [ObserveUserAuthStateUseCase].
 */

const val TEST_USER_ID = "testuser"

class ObserveUserAuthStateUseCaseTest {
    @Rule
    @JvmField
    val instantTaskExecutor = InstantTaskExecutorRule()

    @Test
    fun userSuccessRegistered() {
        val subject = createObserveUserAuthStateUseCase(
            isAnonymous = false,
            isRegistered = true,
            isSuccess = true,
            userId = TEST_USER_ID
        )

        subject.execute(Any())

        val value = LiveDataTestUtil.getValue(subject.observe()) as Result.Success

        assertThat(
            value.data.getUid(),
            `is`(equalTo(TEST_USER_ID))
        )
        assertThat(
            value.data.isSignedIn(),
            `is`(equalTo(true))
        )
        assertThat(
            value.data.isRegistered(),
            `is`(equalTo(true))
        )
    }

    @Test
    fun userSuccessNotRegistered() {
        val subject = createObserveUserAuthStateUseCase(
            isAnonymous = false,
            isRegistered = false,
            isSuccess = true,
            userId = TEST_USER_ID
        )

        subject.execute(Any())

        val value = LiveDataTestUtil.getValue(subject.observe()) as Result.Success

        assertThat(
            value.data.getUid(),
            `is`(equalTo(TEST_USER_ID))
        )
        assertThat(
            value.data.isSignedIn(),
            `is`(equalTo(true))
        )
        assertThat(
            value.data.isRegistered(),
            `is`(equalTo(false))
        )
    }

    @Test
    fun userErrorNotRegistered() {
        val subject = createObserveUserAuthStateUseCase(
            isAnonymous = false,
            isRegistered = true,
            isSuccess = false,
            userId = TEST_USER_ID
        )

        // Start the listeners
        subject.execute(Any())

        val value = LiveDataTestUtil.getValue(subject.observe())

        assertThat(
            value,
            `is`(instanceOf(Result.Error::class.java))
        )
    }

    @Test
    fun userLogsOut() {

        val subject = createObserveUserAuthStateUseCase(
            isAnonymous = true,
            isRegistered = false,
            isSuccess = true,
            userId = TEST_USER_ID
        )

        // Start the listeners
        subject.execute(Any())

        val value = LiveDataTestUtil.getValue(subject.observe()) as Result.Success

        assertThat(
            value.data.getUid(),
            `is`(equalTo(TEST_USER_ID))
        )
        assertThat(
            value.data.isSignedIn(),
            `is`(equalTo(false))
        )
        assertThat(
            value.data.isRegistered(),
            `is`(equalTo(false))
        )
    }

    private fun createObserveUserAuthStateUseCase(
        isAnonymous: Boolean,
        isRegistered: Boolean,
        isSuccess: Boolean,
        userId: String
    ): ObserveUserAuthStateUseCase {
        val authStateUserDataSource = FakeAuthStateUserDataSource(isAnonymous, isSuccess, userId)

        val registeredUserDataSource = FakeRegisteredUserDataSource(isRegistered)

        val subject = ObserveUserAuthStateUseCase(
            registeredUserDataSource, authStateUserDataSource
        )

        return subject
    }
}

class FakeRegisteredUserDataSource(val isRegistered: Boolean) : RegisteredUserDataSource {
    val result = MutableLiveData<Result<Boolean?>?>()

    override fun listenToUserChanges(userId: String) {
        result.postValue(Result.Success(isRegistered))
    }

    override fun observeResult(): LiveData<Result<Boolean?>?> {
        return result
    }

    override fun setAnonymousValue() {
        // Noop
    }
}

class FakeAuthStateUserDataSource(
    private val isAnonymous: Boolean,
    private val successFirebaseUser: Boolean,
    private val userId: String
) : AuthStateUserDataSource {

    private val _userId = MutableLiveData<String?>()

    private val _firebaseUser = MutableLiveData<Result<AuthenticatedUserInfoBasic?>>()

    override fun startListening() {
        _userId.postValue(userId)

        if (successFirebaseUser) {
            val mockUser = mock<AuthenticatedUserInfoBasic> {
                on { isAnonymous() }.doReturn(isAnonymous)
                on { getUid() }.doReturn(TEST_USER_ID)
                on { isSignedIn() }.doReturn(!isAnonymous)
            }
            _firebaseUser.postValue(Result.Success(mockUser))
        } else {
            _firebaseUser.postValue(Result.Error(Exception("Test")))
        }
    }

    override fun getBasicUserInfo(): LiveData<Result<AuthenticatedUserInfoBasic?>> {
        return _firebaseUser
    }

    override fun clearListener() {
        // Noop
    }
}