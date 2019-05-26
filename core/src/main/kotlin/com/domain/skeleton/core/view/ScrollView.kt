package com.domain.skeleton.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import com.domain.skeleton.core.R

class ScrollView : ConstraintLayout {

    lateinit var scroll: NestedScrollView

    lateinit var content: ConstraintLayout

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
            content = ConstraintLayout(context)
            scroll = NestedScrollView(context)

            setupScroll()

            scroll.addView(content)

            addView(scroll)

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

    private fun setupScroll() {
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

        layoutParams.leftToLeft = LayoutParams.PARENT_ID
        layoutParams.rightToRight = LayoutParams.PARENT_ID
        layoutParams.bottomToBottom = LayoutParams.PARENT_ID
        layoutParams.topToTop = LayoutParams.PARENT_ID

        scroll.layoutParams = layoutParams
        scroll.isFillViewport = true
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams?) {
        if (child != scroll) {
            content.addView(child, index, params)
        } else {
            super.addView(child, index, params)
        }
    }
}