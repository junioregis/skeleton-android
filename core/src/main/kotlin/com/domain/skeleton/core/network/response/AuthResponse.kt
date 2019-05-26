package com.domain.skeleton.core.network.response

import okhttp3.Headers

class AuthResponse<T>(val code: Int,
                      val headers: Headers,
                      val body: T) {

    val isSuccessful: Boolean
        get() {
            return code in 200..299
        }
}