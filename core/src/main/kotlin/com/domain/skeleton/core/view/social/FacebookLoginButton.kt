package com.domain.skeleton.core.view.social

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.domain.skeleton.core.R
import com.domain.skeleton.core.ext.getCompatDrawable
import com.facebook.login.widget.LoginButton

class FacebookLoginButton : ConstraintLayout {

    private val layout = R.layout.view_social_login

    private val bg = R.color.colorFacebookBackground

    private val icon = R.drawable.com_facebook_button_icon_blue

    private val label = R.string.social_login_facebook

    val button: LoginButton by lazy {
        LoginButton(context)
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

            val textView = findViewById<AppCompatTextView>(R.id.tv_label)

            textView.setText(label)

            setOnClickListener {
                button.performClick()
            }
        }
    }
}