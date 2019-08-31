package com.kinglloy.iosched.ui

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.kinglloy.iosched.shared.result.EventObserver
import com.kinglloy.iosched.shared.util.checkAllMatched
import com.kinglloy.iosched.shared.util.viewModelProvider
import com.kinglloy.iosched.ui.LaunchDestination.MAIN_ACTIVITY
import com.kinglloy.iosched.ui.LaunchDestination.ONBOARDING
import com.kinglloy.iosched.ui.onboarding.OnboardingActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LauncherActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: LaunchViewModel = viewModelProvider(viewModelFactory)
        viewModel.launchDestination.observe(this, EventObserver { destination ->
            when (destination) {
                MAIN_ACTIVITY -> startActivity(Intent(this, MainActivity::class.java))
                ONBOARDING -> startActivity(Intent(this, OnboardingActivity::class.java))
            }.checkAllMatched
            finish()
        })
    }
}
