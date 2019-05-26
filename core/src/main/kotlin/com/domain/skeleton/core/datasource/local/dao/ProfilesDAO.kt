package com.domain.skeleton.core.datasource.local.dao

import androidx.room.*
import com.domain.skeleton.core.datasource.local.entity.ProfileEntity

@Dao
interface ProfilesDAO {

    @Query("SELECT * FROM profiles WHERE id = :id LIMIT 1")
    fun get(id: String): ProfileEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: ProfileEntity)

    @Update
    fun update(profile: ProfileEntity)

    @Delete
    fun delete(profile: ProfileEntity)
}