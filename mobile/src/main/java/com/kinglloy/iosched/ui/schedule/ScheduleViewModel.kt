package com.kinglloy.iosched.ui.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kinglloy.iosched.model.ConferenceDay
import com.kinglloy.iosched.ui.signin.SignInViewModelDelegate
import javax.inject.Inject

/**
 * Loads data and exposes it to the view.
 * By annotating the constructor with [@Inject], Dagger will use that constructor when needing to
 * create the object, so defining a [@Provides] method for this class won't be needed.
 */
class ScheduleViewModel @Inject constructor(
    signInViewModelDelegate: SignInViewModelDelegate
) : ViewModel(), SignInViewModelDelegate by signInViewModelDelegate {
    val isConferenceTimeZone: LiveData<Boolean>

    init {
        isConferenceTimeZone = MutableLiveData<Boolean>()
    }

    fun scrollToStartOfDay(day: ConferenceDay) {

    }
}