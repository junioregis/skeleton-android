package com.domain.skeleton.core.auth

import com.domain.skeleton.core.AppConfig
import com.domain.skeleton.core.datasource.remote.json.auth.Token
import com.domain.skeleton.core.preference.PrefKeys

class UserCredentials(private val config: AppConfig) {

    val hasCredentials: Boolean
        get() {
            return config.preferences.getString(PrefKeys.AUTH_ACCESS_TOKEN)
                    .isNotEmpty()
        }

    fun save(tokenResponse: Token) = save(OAuth2.from(tokenResponse))

    fun save(auth: OAuth2) {
        config.preferences.set(PrefKeys.AUTH_TOKEN_TYPE, auth.tokenType)
        config.preferences.set(PrefKeys.AUTH_ACCESS_TOKEN, auth.accessToken)
        config.preferences.set(PrefKeys.AUTH_REFRESH_TOKEN, auth.refreshToken)
        config.preferences.set(PrefKeys.AUTH_EXPIRES_IN, auth.expiresIn)
    }

    val oauth2: OAuth2
        get() {
            return OAuth2(
                    config.preferences.getString(PrefKeys.AUTH_TOKEN_TYPE),
                    config.preferences.getString(PrefKeys.AUTH_ACCESS_TOKEN),
                    config.preferences.getString(PrefKeys.AUTH_REFRESH_TOKEN),
                    config.preferences.getInt(PrefKeys.AUTH_EXPIRES_IN))
        }

    class OAuth2(val tokenType: String,
                 val accessToken: String,
                 val refreshToken: String,
                 val expiresIn: Int) {

        companion object {

            fun from(tokenResponse: Token): OAuth2 {
                return OAuth2(
                        tokenResponse.tokenType,
                        tokenResponse.accessToken,
                        tokenResponse.refreshToken,
                        tokenResponse.expiresIn)
            }
        }
    }
}
