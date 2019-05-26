package com.domain.skeleton.profile.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.domain.skeleton.core.datasource.local.entity.ProfileEntity
import com.domain.skeleton.core.ext.load
import com.domain.skeleton.core.navigation.Screen
import com.domain.skeleton.profile.R
import com.domain.skeleton.profile.viewmodel.ProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : ProfileFragment() {

    override val layout = R.layout.fragment_profile

    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        appbar?.visibility = View.GONE
        navView?.visibility = View.VISIBLE

        rootActivity?.showStatusBar()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.data, this::onSuccess)
        observe(viewModel.error, this::callService)

        tv_logout.setOnClickListener {
            viewModel.logout()
            navigator.nav(Screen.AUTH_ROOT)
        }

        if (viewModel.hasCredentials) {
            callService()
        } else {
            unauthorized()
        }
    }

    private fun callService() {
        showLoading()

        viewModel.sync()
    }

    private fun onSuccess(profile: ProfileEntity) {
        hideLoading()

        iv_avatar.load(profile.avatar, true)
        tv_name.text = profile.name
        tv_age.text = getString(R.string.entry_profile_age, profile.age)
    }
}