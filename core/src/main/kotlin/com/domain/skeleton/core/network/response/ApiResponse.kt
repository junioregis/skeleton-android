package com.domain.skeleton.core.network.response

import com.domain.skeleton.core.network.json.Meta
import okhttp3.Headers

class ApiResponse<T>(val code: Int,
                     val headers: Headers,
                     val message: String,
                     val body: T) {

    val isSuccessful: Boolean
        get() {
            return code in 200..299
        }

    data class Envelope<T>(val meta: Meta, val body: T)
}