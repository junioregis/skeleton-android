package com.domain.skeleton.core.datasource.remote.json.me

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Profile(

        @SerializedName("id")
        @Expose
        val id: String = "",

        @SerializedName("name")
        @Expose
        val name: String = "",

        @SerializedName("gender")
        @Expose
        val gender: String = "",

        @SerializedName("age")
        @Expose
        val age: Int = -1,

        @SerializedName("avatar")
        @Expose
        var avatar: String = ""
)