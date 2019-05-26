package com.domain.skeleton.core.activity

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.domain.skeleton.core.R
import com.domain.skeleton.core.ext.AppError
import com.domain.skeleton.core.ext.AppErrorType
import com.domain.skeleton.core.fragment.BaseFragment
import com.domain.skeleton.core.helper.SnackbarHelper
import com.domain.skeleton.core.helper.ToastHelper
import com.domain.skeleton.core.navigation.Navigator
import com.domain.skeleton.core.navigation.Screen
import com.domain.skeleton.core.util.log
import com.domain.skeleton.core.view.AppViews
import com.domain.skeleton.core.view.layout.IActivityLayoutView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

abstract class BaseActivity<V : IActivityLayoutView> : AppCompatActivity() {

    protected abstract val layout: Int

    protected open val theme = R.style.AppTheme_NoActionBar

    lateinit var rootView: V
        private set

    val toast: ToastHelper by inject()

    open val snackbar: SnackbarHelper by lazy {
        SnackbarHelper(rootView.content)
    }

    var closingApp: Boolean = false
        private set

    protected val appViews: AppViews by inject { parametersOf(this) }

    protected val navigator: Navigator by inject()

    val toolbar: Toolbar?
        get() {
            return rootView.toolbar
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        if (theme > 0) {
            setTheme(theme)
        }

        super.onCreate(savedInstanceState)

        setContentView(layout)

        rootView = findViewById<ViewGroup>(android.R.id.content)
                .getChildAt(0) as V
    }

    override fun onPause() {
        snackbar.dismiss()
        toast.dismiss()

        super.onPause()
    }

    fun setupToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        rootView.setupToolbar(toolbar)
    }

    fun nav(screen: Screen, args: Bundle? = null, noHistory: Boolean = true) {
        navigator.nav(screen, args, noHistory)
    }

    fun showLoading(view: View = appViews.loading, above: Boolean = false) = rootView.showLoading(view, above)

    fun hideLoading() = rootView.hideLoading()

    fun setMessage(view: View) = rootView.setMessage(view)

    fun closeApp() {
        if (!closingApp) {
            closingApp = true

            toast.showText("Closing...")

            Handler().postDelayed({ closingApp = false }, CLOSE_APP_DELAY)
        } else {
            toast.dismiss()
            finish()
        }
    }

    fun checkPermissions(vararg permissions: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val deniedPermissions = permissions.filter {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            }.toTypedArray()

            if (deniedPermissions.any()) {
                ActivityCompat.requestPermissions(this, deniedPermissions, RC_PERMISIONS)
            }
        }
    }

    fun hideKeyboard() {
        val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        manager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    fun showStatusBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun hideStatusBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    fun <F : Fragment> loadFragment(@IdRes id: Int,
                                    fragment: F,
                                    runOnCommit: (F) -> Unit) {
        log.d("LOADING FRAGMENT:\n" + fragment.javaClass.canonicalName)

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(id, fragment)
        fragmentTransaction.runOnCommit {
            log.d("FRAGMENT COMMITED:\n" + fragment.javaClass.canonicalName)
            runOnCommit(fragment)
        }
        fragmentTransaction.commit()
    }

    protected fun <T> observe(liveData: LiveData<T>, onSuccess: (T) -> Unit) {
        liveData.observe(this, Observer<T>(onSuccess))
    }

    protected fun observe(liveData: LiveData<AppError>, onRetry: () -> Unit = {}) {
        liveData.observe(this, Observer<AppError> { onError(it) { onRetry.invoke() } })
    }

    open fun onPermissionsResponse(grantedPermissions: Array<out String>, deniedPermissions: Array<out String>) {
        supportFragmentManager?.fragments?.forEach {
            (it as? BaseFragment)?.onPermissionsResponse(grantedPermissions, deniedPermissions)
        }
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
        appViews.unauthorized.setOnClickListener {
            navigator.nav(Screen.AUTH_ROOT)
        }

        rootView.setMessage(appViews.unauthorized)
    }

    @TargetApi(Build.VERSION_CODES.M)
    final override fun onRequestPermissionsResult(requestCode: Int,
                                                  permissions: Array<out String>,
                                                  grantResults: IntArray) {
        when (requestCode) {
            RC_PERMISIONS -> {
                val grantPermissions = arrayListOf<String>()
                val deniedPermissions = arrayListOf<String>()

                if (grantResults.any()) {
                    permissions.forEachIndexed { index, p ->
                        if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                            grantPermissions.add(p)
                        } else {
                            deniedPermissions.add(p)
                        }
                    }
                }

                onPermissionsResponse(grantPermissions.toTypedArray(), deniedPermissions.toTypedArray())
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {

        const val CLOSE_APP_DELAY = 1000L
        const val RC_PERMISIONS = 1001
    }
}