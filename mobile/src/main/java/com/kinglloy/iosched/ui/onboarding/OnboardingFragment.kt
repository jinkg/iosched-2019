package com.kinglloy.iosched.ui.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.databinding.FragmentOnboardingBinding
import com.kinglloy.iosched.shared.util.TimeUtils
import com.kinglloy.iosched.shared.util.viewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

/**
 * Contains the pages of the onboarding experience and responds to [OnboardingViewModel] events.
 */
class OnboardingFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var onboardingViewModel: OnboardingViewModel

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        onboardingViewModel = viewModelProvider(viewModelFactory)

        binding = FragmentOnboardingBinding.inflate(inflater, container, false).apply {
            viewModel = onboardingViewModel
            lifecycleOwner = viewLifecycleOwner
            pager.adapter = OnboardingAdapter(childFragmentManager)
        }

        return binding.root
    }
}

class OnboardingAdapter(fragmentManager: FragmentManager) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val fragments = if (!TimeUtils.conferenceHasStarted()) {
        // Before the conference
        arrayOf(
            WelcomePreConferenceFragment(),
            OnboardingSignInFragment()
        )
    } else if (TimeUtils.conferenceHasStarted() && !TimeUtils.conferenceHasEnded()) {
        // During the conference
        arrayOf(
            WelcomeDuringConferenceFragment(),
            OnboardingSignInFragment()
        )
    } else {
        // Post the conference
        arrayOf(
            WelcomePostConferenceFragment()
        )
    }

    override fun getItem(position: Int) = fragments[position]

    override fun getCount() = fragments.size

}