package com.domain.skeleton.core.network.interceptor

import com.domain.skeleton.core.network.annotation.ApiVersion
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class ApiVersionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)

        val annotation = invocation?.method()
                ?.declaringClass
                ?.getAnnotation(ApiVersion::class.java)

        val request = if (annotation != null) {
            val version = annotation.version

            chain.request().newBuilder()
                    .addHeader("Api-Version", version.toString())
                    .build()
        } else {
            chain.request()
        }

        return chain.proceed(request)
    }
}