package com.domain.skeleton.activity

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import com.domain.skeleton.R
import com.domain.skeleton.core.activity.SimpleActivity
import com.domain.skeleton.core.ext.AppError
import com.domain.skeleton.core.navigation.Screen
import com.domain.skeleton.di.injectSplash
import com.domain.skeleton.viewmodel.SplashViewModel
import kotlinx.android.synthetic.main.activity_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashActivity : SimpleActivity() {

    override val layout = R.layout.activity_splash

    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectSplash()

        rootView.appbar.visibility = View.GONE

        hideStatusBar()

        observe(viewModel.success, this::onSuccess)
        observe(viewModel.error)

        iv_logo.startAnimation(AnimationUtils.loadAnimation(this, R.anim.logo_pulse))

        viewModel.initApp()
    }

    private fun onSuccess(options: SplashViewModel.Options) {
        if (options.firstInit) {
            navigator.nav(Screen.AUTH_ROOT)
        } else {
            navigator.nav(Screen.MAIN_ROOT)
        }
    }

    override fun onError(e: AppError, onRetry: () -> Unit) {
        toast.showText(e.message)
        finish()
    }
}