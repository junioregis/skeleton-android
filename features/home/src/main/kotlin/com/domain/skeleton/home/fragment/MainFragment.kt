package com.domain.skeleton.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domain.skeleton.core.ext.AppError
import com.domain.skeleton.core.view.ToolbarView
import com.domain.skeleton.home.R
import com.domain.skeleton.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : HomeFragment() {

    override val layout = R.layout.fragment_home

    private val viewModel: HomeViewModel by viewModel()

    private val toolbar: ToolbarView
        get() {
            return appViews.toolbar
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appbar?.visibility = View.VISIBLE
        navView?.visibility = View.VISIBLE

        rootActivity?.showStatusBar()
        rootActivity?.setupToolbar(toolbar)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.data, this::onSuccess)
        observe(viewModel.error)

        bt_ping.setOnClickListener {
            pingServer()
        }
    }

    private fun pingServer() {
        toolbar.showLoading()

        tv_response.text = ""
        bt_ping.isEnabled = false

        viewModel.ping()
    }

    private fun onSuccess(message: String) {
        toolbar.hideLoading()

        tv_response.text = message
        bt_ping.isEnabled = true
    }

    override fun onError(e: AppError, onRetry: () -> Unit) {
        toolbar.hideLoading()

        tv_response.text = e.message
        bt_ping.isEnabled = true
    }
}