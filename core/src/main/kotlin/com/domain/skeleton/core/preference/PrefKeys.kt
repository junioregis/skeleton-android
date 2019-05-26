package com.domain.skeleton.core.preference

enum class PrefKeys(val keyName: String, val defaultValue: Any) {

    APP_PREFERENCE_VERSION("app_preference_version", 0),
    APP_FIRST_INIT("app_first_init", true),

    AUTH_USER_ID("auth_user_id", ""),
    AUTH_TOKEN_TYPE("auth_token_type", ""),
    AUTH_ACCESS_TOKEN("auth_access_token", ""),
    AUTH_REFRESH_TOKEN("auth_refresh_token", ""),
    AUTH_EXPIRES_IN("auth_expires_in", 0);
}