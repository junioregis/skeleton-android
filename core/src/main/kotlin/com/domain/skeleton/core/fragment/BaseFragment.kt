package com.domain.skeleton.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.domain.skeleton.core.activity.BaseActivity
import com.domain.skeleton.core.ext.AppError
import com.domain.skeleton.core.ext.AppErrorType
import com.domain.skeleton.core.helper.SnackbarHelper
import com.domain.skeleton.core.helper.ToastHelper
import com.domain.skeleton.core.navigation.Navigator
import com.domain.skeleton.core.navigation.Screen
import com.domain.skeleton.core.view.AppViews
import com.domain.skeleton.core.view.fragment.FragmentView
import com.google.android.material.appbar.AppBarLayout
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseFragment : Fragment() {

    protected abstract val layout: Int

    lateinit var rootView: FragmentView
        private set

    val rootActivity: BaseActivity<*>?
        get() {
            return activity as? BaseActivity<*>
        }

    val appbar: AppBarLayout?
        get() {
            return rootActivity?.rootView?.appbar
        }

    val toast: ToastHelper?
        get() {
            return rootActivity?.toast
        }

    val snackbar: SnackbarHelper?
        get() {
            return rootActivity?.snackbar
        }

    protected val appViews: AppViews by inject { parametersOf(context) }

    protected val navigator: Navigator by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(layout, container, false) as FragmentView

        return rootView
    }

    open fun onPermissionsResponse(grantedPermissions: Array<out String>, deniedPermissions: Array<out String>) {}

    fun nav(screen: Screen, args: Bundle? = null, noHistory: Boolean = true) {
        navigator.nav(screen, args, noHistory)
    }

    fun showLoading(view: View = appViews.loading, above: Boolean = false) = rootView.showLoading(view, above)

    fun hideLoading() = rootView.hideLoading()

    fun setMessage(view: View) = rootView.setMessage(view)

    fun <F : Fragment> loadFragment(@IdRes id: Int, fragment: F, runOnCommit: (F) -> Unit) {
        rootActivity?.loadFragment(id, fragment, runOnCommit)
    }

    protected fun <T> observe(liveData: LiveData<T>, onSuccess: (T) -> Unit) {
        liveData.observe(this, Observer<T>(onSuccess))
    }

    protected fun observe(liveData: LiveData<AppError>, onRetry: () -> Unit = {}) {
        liveData.observe(this, Observer<AppError> { onError(it) { onRetry.invoke() } })
    }

    protected open fun onError(e: AppError, onRetry: () -> Unit) {
        when (e.type) {
            AppErrorType.UNAUTHORIZED -> unauthorized()
            else -> {
                appViews.customError.message.text = e.message
                appViews.customError.setOnClickListener {
                    onRetry.invoke()
                }

                rootView.setMessage(appViews.customError)
            }
        }
    }

    protected open fun unauthorized() {
        appViews.unauthorized.action.setOnClickListener {
            navigator.nav(Screen.AUTH_ROOT)
        }

        rootView.setMessage(appViews.unauthorized)
    }
}