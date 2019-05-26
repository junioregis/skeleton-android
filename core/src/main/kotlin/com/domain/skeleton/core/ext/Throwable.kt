package com.domain.skeleton.core.ext

import android.content.Context
import com.domain.skeleton.core.R
import com.domain.skeleton.core.exception.network.AuthException
import com.domain.skeleton.core.exception.network.ConnectionException
import com.domain.skeleton.core.exception.network.SyncException
import com.domain.skeleton.core.exception.network.UnauthorizedException
import com.domain.skeleton.core.social.Providers

fun Throwable.toAppError(context: Context): AppError {
    val type: AppErrorType
    val message: Int

    when (this) {
        is UnauthorizedException -> {
            type = AppErrorType.UNAUTHORIZED
            message = R.string.msg_unauthorized
        }
        is ConnectionException -> {
            type = AppErrorType.CONNECTION
            message = R.string.err_connection_fail
        }
        is SyncException -> {
            type = AppErrorType.SYNC
            message = R.string.err_cloud_sync
        }
        is AuthException -> {
            type = AppErrorType.AUTH

            message = when (provider) {
                Providers.GOOGLE -> R.string.err_social_google
                Providers.FACEBOOK -> R.string.err_social_facebook
            }
        }
        else -> {
            type = AppErrorType.APP
            message = R.string.err_unknown
        }
    }

    return AppError(type, context.getString(message))
}

data class AppError(val type: AppErrorType, val message: String)

enum class AppErrorType {

    SYNC, SERVER, CONNECTION, AUTH, UNAUTHORIZED, APP
}