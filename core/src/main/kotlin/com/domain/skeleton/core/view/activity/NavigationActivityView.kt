package com.domain.skeleton.core.view.activity

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import com.domain.skeleton.core.R
import com.domain.skeleton.core.view.layout.IActivityLayoutView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class NavigationActivityView : ConstraintLayout, IActivityLayoutView {

    override val layout = R.layout.layout_activity_navigation

    override val appbar: AppBarLayout by lazy {
        findViewById<AppBarLayout>(R.id.appbar)
    }

    override val toolbar: Toolbar? by lazy {
        appbar.getChildAt(0) as? Toolbar
    }

    override val loading: ConstraintLayout by lazy {
        findViewById<ConstraintLayout>(R.id.loading)
    }

    override val message: ConstraintLayout by lazy {
        findViewById<ConstraintLayout>(R.id.message)
    }

    override val content: ConstraintLayout by lazy {
        findViewById<ConstraintLayout>(R.id.content)
    }

    override val info: TextView by lazy {
        findViewById<AppCompatTextView>(R.id.info)
    }

    val nav: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.menu)
    }

    override var isLoading = false

    private var layoutInflated = false

    constructor(context: Context) : super(context) {
        setup()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setup(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setup(attrs)
    }

    private fun setup(attrs: AttributeSet? = null) {
        if (!isInEditMode) {
            inflate(context, layout, this)

            if (attrs != null) {
                context.theme.obtainStyledAttributes(
                        attrs,
                        R.styleable.View,
                        0, 0).apply {
                    try {
                        val padding = getDimension(R.styleable.View_contentPadding, 0f)
                                .toInt()

                        content.setPadding(padding, padding, padding, padding)
                    } finally {
                        recycle()
                    }
                }
            }
        }
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (layoutInflated) {
            content.addView(child, index, params)
        } else {
            super.addView(child, index, params)
            layoutInflated = true
        }
    }

    override fun setupToolbar(toolbar: Toolbar) {
        appbar.removeAllViews()
        appbar.addView(toolbar)
    }

    override fun onInterceptTouchEvent(e: MotionEvent) = isLoading

    override fun showLoading(view: View, above: Boolean) {
        if (isLoading) return

        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        layoutParams.rightToRight = LayoutParams.PARENT_ID
        layoutParams.leftToLeft = LayoutParams.PARENT_ID
        layoutParams.topToTop = LayoutParams.PARENT_ID
        layoutParams.bottomToBottom = LayoutParams.PARENT_ID

        view.layoutParams = layoutParams

        loading.removeAllViews()
        loading.addView(view)

        message.visibility = View.GONE
        loading.visibility = View.VISIBLE

        if (above) {
            content.visibility = View.VISIBLE

            loading.setBackgroundColor(Color.BLACK)
            loading.background.alpha = 100
        } else {
            content.visibility = View.GONE

            loading.setBackgroundColor(Color.TRANSPARENT)
            loading.background.alpha = 255
        }

        isLoading = true
    }

    override fun hideLoading() {
        if (!isLoading) return

        message.visibility = View.GONE
        loading.visibility = View.INVISIBLE
        content.visibility = View.VISIBLE

        isLoading = false
    }

    override fun setMessage(view: View) {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        layoutParams.rightToRight = LayoutParams.PARENT_ID
        layoutParams.leftToLeft = LayoutParams.PARENT_ID
        layoutParams.topToTop = LayoutParams.PARENT_ID
        layoutParams.bottomToBottom = LayoutParams.PARENT_ID

        view.layoutParams = layoutParams

        message.removeAllViews()
        message.addView(view)

        loading.visibility = View.GONE
        message.visibility = View.VISIBLE
        content.visibility = View.GONE
    }

    override fun setInfo(text: String) {
        if (text.isBlank()) {
            info.visibility = View.GONE
        } else {
            info.visibility = View.VISIBLE
        }

        info.text = text
    }
}