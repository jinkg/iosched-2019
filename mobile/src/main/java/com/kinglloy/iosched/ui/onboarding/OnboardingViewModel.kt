package com.kinglloy.iosched.ui.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinglloy.iosched.shared.domain.prefs.OnboardingCompleteActionUseCase
import com.kinglloy.iosched.shared.result.Event
import com.kinglloy.iosched.ui.signin.SignInViewModelDelegate
import javax.inject.Inject

/**
 * Yalin on 2019-08-31
 *
 * Records that onboarding has been completed and navigates user onward.
 */
class OnboardingViewModel @Inject constructor(
    private val onboardingCompleteActionUseCase: OnboardingCompleteActionUseCase,
    signInViewModelDelegate: SignInViewModelDelegate
) : ViewModel(), SignInViewModelDelegate by signInViewModelDelegate {

    private val _navigateToMainActivity = MutableLiveData<Event<Unit>>()
    val navigateToMainActivity: LiveData<Event<Unit>> = _navigateToMainActivity

    private val _navigateToSignInDialogAction = MutableLiveData<Event<Unit>>()
    val navigateToSignInDialogAction: LiveData<Event<Unit>> = _navigateToSignInDialogAction


    fun getStartedClick() {
        onboardingCompleteActionUseCase(true)
        _navigateToMainActivity.postValue(Event(Unit))
    }

    fun onSignInClicked() {
        _navigateToSignInDialogAction.postValue(Event(Unit))
    }
}