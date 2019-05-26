package com.domain.skeleton.core.datasource.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.domain.skeleton.core.datasource.remote.json.me.Profile

@Entity(tableName = "profiles")
data class ProfileEntity(

        @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String = "",

        @ColumnInfo(name = "name")
        val name: String = "",

        @ColumnInfo(name = "gender")
        val gender: String = "",

        @ColumnInfo(name = "age")
        val age: Int = -1,

        @ColumnInfo(name = "avatar")
        val avatar: String = "") {

    companion object {

        fun from(model: Profile) = ProfileEntity(
                model.id,
                model.name,
                model.gender,
                model.age,
                model.avatar)
    }
}