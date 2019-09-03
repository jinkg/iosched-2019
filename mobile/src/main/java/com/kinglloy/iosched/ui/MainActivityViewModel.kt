package com.kinglloy.iosched.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kinglloy.iosched.ui.signin.SignInViewModelDelegate
import javax.inject.Inject

/**
 * Yalin on 2019-09-03
 */
class MainActivityViewModel @Inject constructor(
    signInViewModelDelegate: SignInViewModelDelegate,
    context: Context
) : ViewModel(), SignInViewModelDelegate by signInViewModelDelegate {

    fun onProfileClicked(){

    }
}