package com.domain.skeleton.core.view.layout

import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout

interface IActivityLayoutView : ILayoutView {

    val appbar: AppBarLayout

    val toolbar: Toolbar?

    val info: TextView

    fun setInfo(text: String)

    fun setupToolbar(toolbar: Toolbar)
}