package com.domain.skeleton.core.ext

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.domain.skeleton.core.exception.ResourceNotFoundException

fun Context.getStringId(value: String): Int {
    return resources.getIdentifier(value, "string", packageName)
}

fun Context.getStyle(value: String): Int {
    return resources.getIdentifier(value, "style", packageName)
}

fun Context.getId(value: String): Int {
    return resources.getIdentifier(value, "id", packageName)
}

fun Context.getCompatColorId(value: String): Int {
    val res = resources.getIdentifier(value, "color", packageName)

    return ContextCompat.getColor(this, res)
}

fun Context.getDrawableId(value: String): Int {
    return resources.getIdentifier(value, "drawable", packageName)
}

fun Context.getCompatColor(value: Int): Int {
    return ContextCompat.getColor(this, value)
}

fun Context.getCompatDrawable(value: Int, radius: Int = 0): Drawable {
    return if (radius > 0) {
        val drawable = GradientDrawable()

        drawable.cornerRadius = dpToPx(radius).toFloat()
        drawable.setColor(getCompatColor(value))
        drawable
    } else {
        AppCompatResources.getDrawable(this, value) ?: throw ResourceNotFoundException()
    }
}

fun Context.getCompatTintedDrawable(drawable: Int, color: Int, mode: PorterDuff.Mode = PorterDuff.Mode.SRC_ATOP): Drawable? {
    val result = getCompatDrawable(drawable)

    result.setColorFilter(getCompatColor(color), mode)

    return result
}

fun Context.compatTint(value: TextView, @ColorRes color: Int) {
    value.setTextColor(getCompatColor(color))
}

fun Context.compatTint(value: ImageView, @ColorRes color: Int) {
    value.setColorFilter(getCompatColor(color))
}

fun Context.compatTint(value: Drawable, @ColorRes color: Int) {
    DrawableCompat.setTint(DrawableCompat.wrap(value), getCompatColor(color))
}

fun Context.getColorHex(value: Int): String {
    val sb = StringBuilder(Integer.toHexString(getCompatColor(value)))

    return sb.substring(2, sb.length)
}