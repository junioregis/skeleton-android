package com.domain.skeleton.core.ext

import android.widget.ImageView
import com.bumptech.glide.request.RequestOptions
import com.domain.skeleton.core.GlideApp

fun ImageView.load(source: Any, circle: Boolean = false) {
    val request = GlideApp.with(context)
            .load(source)

    if (circle) {
        request.apply(RequestOptions.circleCropTransform())
                .into(this)
    } else {
        request.into(this)
    }
}
