package com.kinglloy.iosched.ui.schedule.fiters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kinglloy.iosched.databinding.FragmentScheduleFilterBinding
import dagger.android.support.DaggerFragment

/**
 * Fragment that shows the list of filters for the Schedule
 */
class ScheduleFilterFragment : DaggerFragment() {

    private lateinit var binding: FragmentScheduleFilterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleFilterBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }
}