package com.kinglloy.iosched.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.databinding.FragmentOnboardingWelcomeDuringBinding
import com.kinglloy.iosched.shared.util.activityViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Yalin on 2019-08-31
 * First page of onboarding showing a welcome message during the conference.
 */
class WelcomeDuringConferenceFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: FragmentOnboardingWelcomeDuringBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOnboardingWelcomeDuringBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.activityViewModel = activityViewModelProvider(viewModelFactory)
    }
}