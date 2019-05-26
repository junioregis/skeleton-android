package com.domain.skeleton.core.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.domain.skeleton.core.R

class LoadingView : ConstraintLayout {

    private val layout = R.layout.view_loading

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
}