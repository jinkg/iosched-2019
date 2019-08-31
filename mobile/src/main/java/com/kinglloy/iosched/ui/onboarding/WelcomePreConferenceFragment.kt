package com.kinglloy.iosched.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.databinding.FragmentOnboardingWelcomePreBinding
import com.kinglloy.iosched.shared.util.activityViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Yalin on 2019-08-31
 * First page of onboarding showing a welcome message before the conference.
 */
class WelcomePreConferenceFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentOnboardingWelcomePreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingWelcomePreBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            activityViewModel = activityViewModelProvider(viewModelFactory)
        }
        return binding.root
    }
}