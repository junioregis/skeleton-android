package com.domain.skeleton.core.fragment

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.domain.skeleton.core.activity.NavigationActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class HostFragment : BaseFragment() {

    val navView: BottomNavigationView?
        get() {
            return (rootActivity as? NavigationActivity)
                    ?.rootView
                    ?.nav
        }

    val navController: NavController
        get() {
            return NavHostFragment.findNavController(this)
        }

    fun nav(resId: Int, args: Bundle? = null) {
        navigator.nav(navController, resId, args)
    }
}