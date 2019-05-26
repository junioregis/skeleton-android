package com.domain.skeleton.auth.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.domain.skeleton.auth.R
import com.domain.skeleton.auth.di.injectFeature
import com.domain.skeleton.auth.viewmodel.AuthViewModel
import com.domain.skeleton.core.activity.SimpleActivity
import com.domain.skeleton.core.ext.AppError
import com.domain.skeleton.core.navigation.Screen
import com.domain.skeleton.core.social.User
import kotlinx.android.synthetic.main.activity_auth.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AuthActivity : SimpleActivity() {

    override val layout = R.layout.activity_auth

    private val viewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectFeature()

        rootView.appbar.visibility = View.GONE

        hideStatusBar()

        viewModel.setFacebookButton(bt_facebook) { showLoading(above = true) }
        viewModel.setGoogleButton(bt_google) { showLoading(above = true) }
        viewModel.setLoginLaterButton(bt_later, this::onLoginLater)

        observe(viewModel.login, this::onSuccess)
        observe(viewModel.error)

        tv_privacy_policy.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)

            intent.data = Uri.parse(getString(R.string.app_privacy_policy_url))

            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel.handleResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun onSuccess(user: User) {
        navigator.nav(Screen.MAIN_ROOT)
    }

    override fun onError(e: AppError, onRetry: () -> Unit) {
        hideLoading()
        snackbar.show(e.message)
    }

    private fun onLoginLater() {
        navigator.nav(Screen.MAIN_ROOT)
    }
}