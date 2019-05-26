package com.domain.skeleton.core.datasource.remote.json.auth

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Token {

    @SerializedName("token_type")
    @Expose
    var tokenType: String = ""

    @SerializedName("expires_in")
    @Expose
    var expiresIn: Int = -1

    @SerializedName("access_token")
    @Expose
    var accessToken: String = ""

    @SerializedName("refresh_token")
    @Expose
    var refreshToken: String = ""
}