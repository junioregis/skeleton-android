package com.domain.skeleton.core.view.social

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.domain.skeleton.core.R
import com.domain.skeleton.core.ext.compatTint
import com.domain.skeleton.core.ext.getCompatDrawable
import com.google.android.gms.common.SignInButton

class GoogleLoginButton : ConstraintLayout {

    private val layout = R.layout.view_social_login

    private val bg = R.color.colorGoogleBackground

    private val icon = R.drawable.googleg_standard_color_18

    private val label = R.string.social_login_google

    val button: SignInButton by lazy {
        SignInButton(context)
    }

    constructor(context: Context) : super(context) {
        config()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        config()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        config()
    }

    private fun config() {
        if (!isInEditMode) {
            inflate(context, layout, this)

            background = context.getCompatDrawable(bg, 25)

            val iconView = findViewById<AppCompatImageView>(R.id.iv_icon)

            iconView.setImageResource(icon)

            context.compatTint(iconView, android.R.color.white)

            val textView = findViewById<AppCompatTextView>(R.id.tv_label)

            textView.setText(label)

            setOnClickListener {
                button.getChildAt(0).performClick()
            }
        }
    }
}