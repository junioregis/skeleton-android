package com.domain.skeleton.home.viewmodel

import androidx.lifecycle.MutableLiveData
import com.domain.skeleton.core.repository.ServerRepository
import com.domain.skeleton.core.viewmodel.BaseViewModel

class HomeViewModel(private val repository: ServerRepository) : BaseViewModel() {

    val data = MutableLiveData<String>()

    fun ping() {
        launch({
            val response = repository.ping()
            val message = response.message

            data.postValue(message)
        })
    }
}