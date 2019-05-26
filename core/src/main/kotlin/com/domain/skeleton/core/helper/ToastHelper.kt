package com.domain.skeleton.core.helper

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast

class ToastHelper(private val context: Context) {

    private lateinit var toast: Toast

    fun showText(@StringRes text: Int, duration: Int = Toast.LENGTH_SHORT) {
        showText(context.getString(text), duration)
    }

    fun showText(text: String, duration: Int = Toast.LENGTH_SHORT) {
        dismiss()

        toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    fun showView(@LayoutRes layout: Int, duration: Int = Toast.LENGTH_SHORT) {
        val view = LayoutInflater.from(context)
                .inflate(layout, null) as View

        showView(view, duration)
    }

    fun showView(view: View, duration: Int = Toast.LENGTH_SHORT) {
        dismiss()

        toast = Toast.makeText(context, null, duration)
        toast.view = view
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    fun dismiss() {
        if (::toast.isInitialized) {
            toast.cancel()
        }
    }
}
