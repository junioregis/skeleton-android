package com.domain.skeleton.core.network.adapter

import android.content.Context
import com.domain.skeleton.core.R
import com.domain.skeleton.core.exception.network.ConnectionException
import com.domain.skeleton.core.exception.network.ServerException
import com.domain.skeleton.core.exception.network.UnauthorizedException
import com.domain.skeleton.core.network.json.Empty
import com.domain.skeleton.core.network.response.ApiResponse
import com.domain.skeleton.core.repository.AuthRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type

class ApiAdapter<T : ApiResponse.Envelope<*>>(val context: Context,
                                              val responseType: Type,
                                              val authRepository: AuthRepository) : CallAdapter<T, Deferred<ApiResponse<*>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Deferred<ApiResponse<*>> {
        val deferred = CompletableDeferred<ApiResponse<*>>()

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }

        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()

                if (body != null) {
                    if (body.body is Empty) {
                        deferred.complete(ApiResponse(response.code(), response.headers(), body.meta.message, Empty()))
                    } else {
                        deferred.complete(ApiResponse(response.code(), response.headers(), body.meta.message, body.body))
                    }
                } else {
                    when (response.code()) {
                        401 -> {
                            try {
                                runBlocking {
                                    authRepository.refreshToken()
                                }

                                call.clone().enqueue(this)
                            } catch (e: Exception) {
                                deferred.completeExceptionally(UnauthorizedException())
                            }
                        }
                        500 -> deferred.completeExceptionally(ServerException())
                        else -> {
                            deferred.complete(ApiResponse(response.code(), response.headers(), body?.meta?.message
                                    ?: context.getString(R.string.err_unknown), Empty()))
                        }
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val e = when {
                    t is IOException || t.cause is IOException -> ConnectionException(t)
                    else -> ServerException(t)
                }

                deferred.completeExceptionally(e)
            }
        })

        return deferred
    }
}