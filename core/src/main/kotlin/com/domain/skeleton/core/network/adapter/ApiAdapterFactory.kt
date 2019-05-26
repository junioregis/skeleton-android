package com.domain.skeleton.core.network.adapter

import android.content.Context
import com.domain.skeleton.core.network.response.ApiResponse
import com.domain.skeleton.core.repository.AuthRepository
import kotlinx.coroutines.Deferred
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ApiAdapterFactory(val context: Context, val authRepository: AuthRepository) : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<out Annotation>, retrofit: Retrofit): CallAdapter<ApiResponse.Envelope<*>, *>? {
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
            return null
        }

        return ApiAdapter(context, responseType, authRepository)
    }
}