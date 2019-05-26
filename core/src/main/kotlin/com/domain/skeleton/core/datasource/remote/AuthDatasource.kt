package com.domain.skeleton.core.datasource.remote

import com.domain.skeleton.core.datasource.remote.json.auth.Token
import com.domain.skeleton.core.network.json.Empty
import com.domain.skeleton.core.network.response.AuthResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AuthDatasource {

    @FormUrlEncoded
    @POST("/oauth/token")
    fun token(@Field("grant_type") grantType: String,
              @Field("provider") provider: String,
              @Field("assertion") assertion: String): Deferred<AuthResponse<Token>>

    @FormUrlEncoded
    @POST("/oauth/token")
    fun refreshToken(@Field("grant_type") grantType: String,
                     @Field("refresh_token") refreshToken: String): Deferred<AuthResponse<Token>>

    @FormUrlEncoded
    @POST("/oauth/revoke")
    fun revoke(@Field("token") token: String): Deferred<AuthResponse<Empty>>
}