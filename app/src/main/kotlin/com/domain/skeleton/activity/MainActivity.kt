package com.domain.skeleton.activity

import android.os.Bundle
import com.domain.skeleton.R
import com.domain.skeleton.core.activity.NavigationActivity
import com.domain.skeleton.core.network.NetworkMonitor
import com.domain.skeleton.di.injectApp
import com.domain.skeleton.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : NavigationActivity() {

    override val menu = R.menu.menu_main

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectApp()

        navController.setGraph(R.navigation.nav_main)

        observe(viewModel.network, this::onNetworkStateChanged)
    }

    private fun onNetworkStateChanged(info: NetworkMonitor.Info) {
        val text = when (info.status) {
            NetworkMonitor.Status.DISCONNECTED -> getString(R.string.err_no_connection)
            else -> {
                ""
            }
        }

        rootView.setInfo(text)
    }
}