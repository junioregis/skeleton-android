package com.domain.skeleton.core.network.adapter

import com.domain.skeleton.core.network.json.Empty
import kotlinx.coroutines.Deferred
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class AuthAdapterFactory : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<*, *>? {
        if (Deferred::class.java != getRawType(returnType)) {
            return null
        }

        if (returnType !is ParameterizedType) {
            return null
        }

        val arg = returnType.actualTypeArguments.first()

        val responseType = if (arg is ParameterizedType) {
            arg.actualTypeArguments.first()
        } else {
            getRawType(Empty::class.java)
        }

        return AuthAdapter<Any>(responseType)
    }
}