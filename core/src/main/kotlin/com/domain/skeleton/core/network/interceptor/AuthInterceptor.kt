package com.domain.skeleton.core.network.interceptor

import com.domain.skeleton.core.auth.UserCredentials
import com.domain.skeleton.core.network.annotation.NoAuth
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

class AuthInterceptor(val credentials: UserCredentials) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request().tag(Invocation::class.java)

        val annotation = invocation?.method()
                ?.getAnnotation(NoAuth::class.java)

        val request = if (annotation == null) {
            chain.request().newBuilder()
                    .addHeader("Authorization", "${credentials.oauth2.tokenType} ${credentials.oauth2.accessToken}")
                    .build()
        } else {
            chain.request()
        }

        return chain.proceed(request)
    }
}