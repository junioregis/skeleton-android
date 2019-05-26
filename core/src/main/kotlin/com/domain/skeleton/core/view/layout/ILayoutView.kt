package com.domain.skeleton.core.view.layout

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

interface ILayoutView {

    val layout: Int

    val loading: ConstraintLayout

    val message: ConstraintLayout

    val content: ConstraintLayout

    var isLoading: Boolean

    fun showLoading(view: View, above: Boolean)

    fun hideLoading()

    fun setMessage(view: View)
}