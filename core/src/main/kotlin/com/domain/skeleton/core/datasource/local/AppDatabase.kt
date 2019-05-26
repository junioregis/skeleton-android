package com.domain.skeleton.core.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.domain.skeleton.core.datasource.local.dao.ProfilesDAO
import com.domain.skeleton.core.datasource.local.entity.ProfileEntity

@Database(entities = [ProfileEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun profilesDao(): ProfilesDAO

    fun reset() = clearAllTables()
}