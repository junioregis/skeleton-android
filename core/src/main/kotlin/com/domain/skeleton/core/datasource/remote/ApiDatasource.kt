package com.domain.skeleton.core.datasource.remote

import com.domain.skeleton.core.datasource.remote.json.me.Profile
import com.domain.skeleton.core.network.annotation.ApiVersion
import com.domain.skeleton.core.network.annotation.NoAuth
import com.domain.skeleton.core.network.json.Empty
import com.domain.skeleton.core.network.response.ApiResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

@ApiVersion(1)
interface ApiDatasource {

    @NoAuth
    @GET("/server/ping")
    fun serverPing(): Deferred<ApiResponse<Empty>>

    @GET("/me/profile")
    fun meProfile(): Deferred<ApiResponse<Profile>>
}