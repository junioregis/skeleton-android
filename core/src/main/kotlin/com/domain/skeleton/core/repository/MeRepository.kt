package com.domain.skeleton.core.repository

import com.domain.skeleton.core.datasource.local.entity.ProfileEntity
import com.domain.skeleton.core.exception.network.SyncException
import com.domain.skeleton.core.exception.network.UnauthorizedException
import com.domain.skeleton.core.preference.AppPreferences
import com.domain.skeleton.core.preference.PrefKeys
import com.domain.skeleton.core.util.log

class MeRepository(private val preferences: AppPreferences) : ApiRepository() {

    private val userId: String
        get() {
            return preferences.getString(PrefKeys.AUTH_USER_ID)
        }

    val profile: ProfileEntity?
        get() {
            return config.database.profilesDao().get(userId)
        }

    suspend fun sync(): ProfileEntity {
        try {
            val remote = datasource.meProfile().await()

            if (remote.isSuccessful) {
                val entity = ProfileEntity.from(remote.body)

                config.database.profilesDao().insert(entity)

                preferences.set(PrefKeys.AUTH_USER_ID, remote.body.id)
            }
        } catch (e: UnauthorizedException) {
            throw e
        } catch (e: Exception) {
            log.e(e)
        }

        return profile ?: throw SyncException()
    }
}