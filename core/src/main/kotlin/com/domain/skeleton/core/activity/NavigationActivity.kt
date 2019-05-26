package com.domain.skeleton.core.activity

import android.content.Intent
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.domain.skeleton.core.R
import com.domain.skeleton.core.fragment.HostFragment
import com.domain.skeleton.core.helper.SnackbarHelper
import com.domain.skeleton.core.view.activity.NavigationActivityView

abstract class NavigationActivity : BaseActivity<NavigationActivityView>() {

    override val layout = R.layout.activity_navigation

    protected abstract val menu: Int

    override val snackbar: SnackbarHelper by lazy {
        SnackbarHelper(rootView.nav)
    }

    val navHost: NavHostFragment?
        get() {
            return supportFragmentManager.fragments
                    .first() as? NavHostFragment
        }

    val navController: NavController
        get() {
            return Navigation.findNavController(this, R.id.host)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        NavigationUI.setupWithNavController(rootView.nav, navController)

        menuInflater.inflate(menu, rootView.nav.menu)

        rootView.nav.setOnNavigationItemSelectedListener { item ->
            if (!item.isChecked) {
                if (arrayOf(R.id.home, R.id.screen_profile).contains(item.itemId)) {
                    navController.popBackStack(item.itemId, true)
                }

                NavigationUI.onNavDestinationSelected(item, navController)
            } else {
                false
            }
        }

        navController.addOnDestinationChangedListener { _, _, _ ->
            toolbar?.title = navController.currentDestination?.label
        }
    }

    override fun onPermissionsResponse(grantedPermissions: Array<out String>, deniedPermissions: Array<out String>) {
        navHost?.childFragmentManager?.fragments
                ?.forEach {
                    (it as? HostFragment)?.onPermissionsResponse(grantedPermissions, deniedPermissions)
                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        navHost?.childFragmentManager?.fragments
                ?.forEach {
                    it.onActivityResult(requestCode, resultCode, data)
                }
    }

    override fun onSupportNavigateUp() = navController.navigateUp()

    fun nav(resId: Int, args: Bundle? = null) {
        navigator.nav(navController, resId, args)
    }
}