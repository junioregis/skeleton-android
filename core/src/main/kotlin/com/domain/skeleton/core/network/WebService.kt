package com.domain.skeleton.core.network

import android.content.Context
import com.domain.skeleton.core.AppConfig
import com.domain.skeleton.core.datasource.remote.ApiDatasource
import com.domain.skeleton.core.datasource.remote.AuthDatasource
import com.domain.skeleton.core.network.adapter.ApiAdapterFactory
import com.domain.skeleton.core.network.adapter.AuthAdapterFactory
import com.domain.skeleton.core.network.converter.ApiBodyConverterFactory
import com.domain.skeleton.core.network.interceptor.ApiVersionInterceptor
import com.domain.skeleton.core.network.interceptor.AuthInterceptor
import com.domain.skeleton.core.repository.AuthRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WebService(private val context: Context,
                 private val config: AppConfig) {

    private val client: OkHttpClient
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()

            loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

            return OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(loggingInterceptor)
                    .addInterceptor {
                        val request = it.request().newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .build()

                        it.proceed(request)
                    }
                    .build()
        }

    fun auth(): AuthDatasource {
        val retrofit = Retrofit.Builder()
                .baseUrl(config.serverUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(AuthAdapterFactory())
                .build()

        return retrofit.create(AuthDatasource::class.java)
    }

    fun api(authRepository: AuthRepository): ApiDatasource {
        val client = client.newBuilder()
                .addInterceptor(ApiVersionInterceptor())
                .addInterceptor(AuthInterceptor(authRepository.credentials))
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl(config.serverUrl)
                .client(client)
                .addConverterFactory(ApiBodyConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(ApiAdapterFactory(context, authRepository))
                .build()

        return retrofit.create(ApiDatasource::class.java)
    }

    companion object {

        private const val TIMEOUT = 15L
    }
}
