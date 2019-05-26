package com.domain.skeleton.core.util

import com.orhanobut.logger.Logger

object AppLogger {

    fun v(message: Any) = Logger.v(message.toString())

    fun d(message: Any) = Logger.d(message.toString())

    fun i(message: Any) = Logger.i(message.toString())

    fun w(message: Any) = Logger.w(message.toString())

    fun e(message: Any) = Logger.e(message.toString())
}

val log: AppLogger
    get() = AppLogger