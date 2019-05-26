package com.domain.skeleton.core.ext

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue

fun Context.dpToPx(value: Int): Int {
    val displayMetrics = this.resources.displayMetrics

    return Math.round(value * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.pxToDp(value: Int): Int {
    val displayMetrics = resources.displayMetrics

    return Math.round(value / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
}

fun Context.spToPx(value: Int): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), resources.displayMetrics)
            .toInt()
}

fun Context.dpToSp(value: Int): Int {
    return (dpToPx(value) / spToPx(value).toFloat()).toInt()
}