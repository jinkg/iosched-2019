package com.kinglloy.iosched.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kinglloy.iosched.R

/**
 * Yalin on 2019-08-31
 * First page of onboarding showing a welcome message post the conference.
 */
class WelcomePostConferenceFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_onboarding_welcome_post, container, false)
    }
}