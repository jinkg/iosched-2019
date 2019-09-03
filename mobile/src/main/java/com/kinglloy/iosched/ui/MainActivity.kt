package com.kinglloy.iosched.ui

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.updatePadding
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.kinglloy.iosched.R
import com.kinglloy.iosched.databinding.NavigationHeaderBinding
import com.kinglloy.iosched.util.*
import com.kinglloy.iosched.widget.HashtagIoDecoration
import com.kinglloy.iosched.widget.NavigationBarContentFrameLayout
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Yalin on 2019-08-31
 */
class MainActivity : DaggerAppCompatActivity(), NavigationHost {

    companion object {
        /** Key for an int extra defining the initial navigation target. */
        const val EXTRA_NAVIGATION_ID = "extra.NAVIGATION_ID"

        private const val NAV_ID_NONE = -1

        private const val DIALOG_SIGN_IN = "dialog_sign_in"
        private const val DIALOG_SIGN_OUT = "dialog_sign_out"

        private val TOP_LEVEL_DESTINATIONS = setOf(
            R.id.navigation_feed,
            R.id.navigation_schedule,
            R.id.navigation_map,
            R.id.navigation_info,
            R.id.navigation_agenda,
            R.id.navigation_codelabs,
            R.id.navigation_settings
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var content: FrameLayout
    private lateinit var drawer: DrawerLayout
    private lateinit var navigation: NavigationView
    private lateinit var navHeaderBinding: NavigationHeaderBinding
    private lateinit var navController: NavController

    private lateinit var statusScrim: View

    private var currentNavId = NAV_ID_NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val drawerContainer: NavigationBarContentFrameLayout = findViewById(R.id.drawer_container)

        drawerContainer.setOnApplyWindowInsetsListener { v, insets ->
            v.onApplyWindowInsets(insets)

            v.updatePadding(
                left = insets.systemWindowInsetLeft,
                right = insets.systemWindowInsetRight
            )

            insets.replaceSystemWindowInsets(
                0, insets.systemWindowInsetTop,
                0, insets.systemWindowInsetBottom
            )
        }

        content = findViewById(R.id.content_container)
        content.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // Make the content ViewGroup ignore insets so that it does not use the default padding
        content.setOnApplyWindowInsetsListener(NoopWindowInsetsListener)

        statusScrim = findViewById(R.id.status_bar_scrim)
        statusScrim.setOnApplyWindowInsetsListener(HeightTopWindowInsetsListener)

        drawer = findViewById(R.id.drawer)

        navHeaderBinding = NavigationHeaderBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
        }

        navController = findNavController(R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentNavId = destination.id
            val isTopLevelDestination = TOP_LEVEL_DESTINATIONS.contains(destination.id)
            val lockMode = if (isTopLevelDestination) {
                DrawerLayout.LOCK_MODE_UNLOCKED
            } else {
                DrawerLayout.LOCK_MODE_LOCKED_CLOSED
            }
            drawer.setDrawerLockMode(lockMode)
        }

        navigation = findViewById(R.id.navigation)
        navigation.apply {
            // Add the #io19 decoration
            val menuView = findViewById<RecyclerView>(R.id.design_navigation_view)?.apply {
                addItemDecoration(HashtagIoDecoration(context))
            }

            // Update the Navigation header view to pad itself down
            navHeaderBinding.root.doOnApplyWindowInsets { v, insets, padding ->
                v.updatePadding(top = padding.top + insets.systemWindowInsetTop)
                // NavigationView doesn't dispatch insets to the menu view, so pad the bottom here.
                menuView?.updatePadding(bottom = insets.systemWindowInsetBottom)
            }
            addHeaderView(navHeaderBinding.root)

            itemBackground = navigationItembackground(context)

            setupWithNavController(navController)
        }

        if (savedInstanceState == null) {
            val initialNavId = intent.getIntExtra(EXTRA_NAVIGATION_ID, R.id.navigation_feed)
            navigation.setCheckedItem(initialNavId)
            navigateTo(initialNavId)
        }
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(navigation) && drawer.shouldCloseDrawerFromBackPress()) {
            closeDrawer()
        } else {
            super.onBackPressed()
        }

    }

    private fun closeDrawer() {
        drawer.closeDrawer(GravityCompat.START)
    }

    override fun registerToolbarWithNavigation(toolbar: Toolbar) {
        val appBarConfiguration = AppBarConfiguration(TOP_LEVEL_DESTINATIONS, drawer)
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    private fun navigateTo(navId: Int) {
        if (navId == currentNavId) {
            return // user tapped the current item
        }
        navController.navigate(navId)
    }
}