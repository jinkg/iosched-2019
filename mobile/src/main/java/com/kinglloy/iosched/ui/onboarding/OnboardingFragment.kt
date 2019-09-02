package com.kinglloy.iosched.ui.onboarding

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.databinding.FragmentOnboardingBinding
import com.kinglloy.iosched.shared.result.EventObserver
import com.kinglloy.iosched.shared.util.TimeUtils
import com.kinglloy.iosched.shared.util.viewModelProvider
import com.kinglloy.iosched.ui.MainActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

private const val AUTO_ADVANCE_DELAY = 6_000L
private const val INITIAL_ADVANCE_DELAY = 3_000L

/**
 * Contains the pages of the onboarding experience and responds to [OnboardingViewModel] events.
 */
class OnboardingFragment : DaggerFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var onboardingViewModel: OnboardingViewModel

    private lateinit var binding: FragmentOnboardingBinding

    private lateinit var pagerPager: ViewPagerPager

    private val handler = Handler()

    private val advancePager: Runnable = object : Runnable {
        override fun run() {
            pagerPager.advance()
            handler.postDelayed(this, AUTO_ADVANCE_DELAY)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
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
            pagerPager = ViewPagerPager(pager)

            pager.setOnTouchListener { _, _ ->
                handler.removeCallbacks(advancePager)
                false
            }
        }

        onboardingViewModel.navigateToMainActivity.observe(this, EventObserver {
            requireActivity().run {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        })

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        handler.postDelayed(advancePager, INITIAL_ADVANCE_DELAY)
    }

    override fun onDetach() {
        handler.removeCallbacks(advancePager)
        super.onDetach()
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