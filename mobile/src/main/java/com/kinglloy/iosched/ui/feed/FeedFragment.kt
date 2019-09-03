package com.kinglloy.iosched.ui.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.databinding.FragmentFeedBinding
import com.kinglloy.iosched.shared.util.activityViewModelProvider
import com.kinglloy.iosched.shared.util.viewModelProvider
import com.kinglloy.iosched.ui.MainNavigationFragment
import com.kinglloy.iosched.ui.signin.setupProfileMenuItem
import com.kinglloy.iosched.util.doOnApplyWindowInsets
import javax.inject.Inject

/**
 * Yalin on 2019-09-03
 */
class FeedFragment : MainNavigationFragment() {
    companion object {
        private const val DIALOG_NEED_TO_SIGN_IN = "dialog_need_to_sign_in"
        private const val BUNDLE_KEY_SESSIONS_LAYOUT_MANAGER_STATE = "sessions_layout_manager"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var model: FeedViewModel
    private lateinit var binding: FragmentFeedBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        model = viewModelProvider(viewModelFactory)
        binding = FragmentFeedBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = model
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setupProfileMenuItem(activityViewModelProvider(viewModelFactory), this)

        binding.root.doOnApplyWindowInsets { _, insets, _ ->
            binding.statusBar.run {
                layoutParams.height = insets.systemWindowInsetTop
                isVisible = layoutParams.height > 0
                requestLayout()
            }
        }
    }
}