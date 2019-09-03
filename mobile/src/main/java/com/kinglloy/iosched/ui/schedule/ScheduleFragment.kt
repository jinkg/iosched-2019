package com.kinglloy.iosched.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.databinding.FragmentScheduleBinding
import com.kinglloy.iosched.shared.util.viewModelProvider
import com.kinglloy.iosched.ui.MainNavigationFragment
import javax.inject.Inject

/**
 * The Schedule page of the top-level Activity.
 */
class ScheduleFragment : MainNavigationFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var scheduleViewModel: ScheduleViewModel

    private lateinit var binding: FragmentScheduleBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scheduleViewModel = viewModelProvider(viewModelFactory)
        binding = FragmentScheduleBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = scheduleViewModel
        }
        return binding.root
    }
}