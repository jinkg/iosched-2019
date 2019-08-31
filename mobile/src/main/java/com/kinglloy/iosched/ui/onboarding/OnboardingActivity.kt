package com.kinglloy.iosched.ui.onboarding

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.updatePadding
import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.R
import com.kinglloy.iosched.shared.util.doOnApplyWindowInsets
import com.kinglloy.iosched.shared.util.inTransaction
import com.kinglloy.iosched.shared.util.viewModelProvider
import com.kinglloy.iosched.ui.signin.SignInDialogFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Yalin on 2019-08-31
 */

class OnboardingActivity : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: OnboardingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        viewModel = viewModelProvider(viewModelFactory)

        val decor = window.decorView
        val flags = decor.systemUiVisibility or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        decor.systemUiVisibility = flags

        val container: FrameLayout = findViewById(R.id.fragment_container)
        container.doOnApplyWindowInsets { v, insets, padding ->
            v.updatePadding(top = padding.top + insets.systemWindowInsetTop)
        }

        if (savedInstanceState == null) {
            supportFragmentManager.inTransaction {
                add(R.id.fragment_container, OnboardingFragment())
            }
        }
    }

    private fun openSignInDialog() {
        SignInDialogFragment().show(supportFragmentManager, DIALOG_SIGN_IN)
    }

    companion object {
        private const val DIALOG_SIGN_IN = "dialog_sign_in"
    }
}
