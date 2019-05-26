package com.domain.skeleton.core.helper

import android.view.View
import com.domain.skeleton.core.activity.BaseActivity
import com.google.android.material.snackbar.Snackbar

class SnackbarHelper(private val view: View) {

    private lateinit var snackbar: Snackbar

    constructor(activity: BaseActivity<*>) : this(activity.rootView as View)

    fun show(text: String, actionText: String = "", onActionClick: () -> Unit = {}) {
        snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)

        if (actionText.isNotEmpty()) {
            snackbar.setAction(actionText) { onActionClick() }
        }

        snackbar.show()
    }

    fun dismiss() {
        if (::snackbar.isInitialized) {
            snackbar.dismiss()
        }
    }
}
