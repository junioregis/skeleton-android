package com.domain.skeleton.core.network.adapter

import com.domain.skeleton.core.exception.network.ConnectionException
import com.domain.skeleton.core.exception.network.ServerException
import com.domain.skeleton.core.exception.network.UnauthorizedException
import com.domain.skeleton.core.network.json.Empty
import com.domain.skeleton.core.network.response.AuthResponse
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type
import java.net.ConnectException

class AuthAdapter<T : Any>(private val responseType: Type) : CallAdapter<T, Deferred<AuthResponse<*>>> {

    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Deferred<AuthResponse<*>> {
        val deferred = CompletableDeferred<AuthResponse<*>>()

        deferred.invokeOnCompletion {
            if (deferred.isCancelled) {
                call.cancel()
            }
        }

        call.enqueue(object : Callback<T> {

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()

                if (body != null) {
                    deferred.complete(AuthResponse(response.code(), response.headers(), body))
                } else {
                    when (response.code()) {
                        401 -> deferred.completeExceptionally(UnauthorizedException())
                        500 -> deferred.completeExceptionally(ServerException())
                        else -> {
                            deferred.complete(AuthResponse(response.code(), response.headers(), Empty()))
                        }
                    }
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val e = if (t is ConnectException) {
                    if (t.cause is IOException) {
                        ConnectionException(t)
                    } else {
                        ServerException(t)
                    }
                } else {
                    t
                }

                deferred.completeExceptionally(e)
            }
        })

        return deferred
    }
}