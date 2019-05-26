package com.domain.skeleton.core.repository

import com.domain.skeleton.core.auth.UserCredentials
import com.domain.skeleton.core.datasource.remote.AuthDatasource
import com.domain.skeleton.core.exception.network.AuthException
import com.domain.skeleton.core.exception.network.UnauthorizedException
import com.domain.skeleton.core.social.User
import org.koin.core.inject

class AuthRepository(val credentials: UserCredentials) : AppRepository<AuthDatasource>() {

    override val datasource: AuthDatasource by inject()

    suspend fun getToken(user: User) {
        val response = datasource.token(
                "assertion",
                user.provider.value,
                user.accessToken).await()

        if (response.isSuccessful) {
            credentials.save(response.body)
        } else {
            config.cleanApp()
            throw AuthException(user.provider)
        }
    }

    suspend fun refreshToken() {
        val response = datasource.refreshToken(
                "refresh_token",
                credentials.oauth2.refreshToken).await()

        if (response.isSuccessful) {
            credentials.save(response.body)
        } else {
            config.cleanApp()
            throw UnauthorizedException()
        }
    }

    suspend fun revokeToken() {
        config.cleanApp()
        datasource.revoke(credentials.oauth2.accessToken).await()
    }
}