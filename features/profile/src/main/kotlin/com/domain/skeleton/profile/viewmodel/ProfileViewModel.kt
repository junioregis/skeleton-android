package com.domain.skeleton.profile.viewmodel

import androidx.lifecycle.MutableLiveData
import com.domain.skeleton.core.auth.UserCredentials
import com.domain.skeleton.core.datasource.local.entity.ProfileEntity
import com.domain.skeleton.core.repository.AuthRepository
import com.domain.skeleton.core.repository.MeRepository
import com.domain.skeleton.core.viewmodel.BaseViewModel

class ProfileViewModel(private val meRepository: MeRepository,
                       private val authRepository: AuthRepository,
                       private val credentials: UserCredentials) : BaseViewModel() {

    val data = MutableLiveData<ProfileEntity>()

    val hasCredentials: Boolean
        get() {
            return credentials.hasCredentials
        }

    fun sync() {
        launch({
            val profile = meRepository.profile

            if (profile != null) {
                data.postValue(profile)
            }

            val response = meRepository.sync()

            data.postValue(response)
        })
    }

    fun logout() {
        launch({
            authRepository.revokeToken()
        })
    }
}
