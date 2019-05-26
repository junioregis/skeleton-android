package com.domain.skeleton.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.domain.skeleton.core.R

class ToolbarView : Toolbar {

    private val layout = R.layout.view_toolbar

    val titleView: TextView by lazy {
        findViewById<AppCompatTextView>(R.id.toolbar_title)
    }

    val loadingView: ProgressBar by lazy {
        findViewById<ProgressBar>(R.id.toolbar_loading)
    }

    constructor(context: Context) : super(context) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup()
    }

    private fun setup() {
        if (!isInEditMode) {
            LayoutInflater.from(context).inflate(layout, this, true)
        }
    }

    override fun setTitle(title: CharSequence?) {
        titleView.text = title
    }

    fun showLoading() {
        loadingView.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loadingView.visibility = View.GONE
    }
}