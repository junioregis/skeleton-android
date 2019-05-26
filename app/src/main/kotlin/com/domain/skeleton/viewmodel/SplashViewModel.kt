package com.domain.skeleton.viewmodel

import androidx.lifecycle.MutableLiveData
import com.domain.skeleton.core.di.injectCore
import com.domain.skeleton.core.preference.AppPreferences
import com.domain.skeleton.core.preference.PrefKeys
import com.domain.skeleton.core.viewmodel.BaseViewModel
import org.koin.core.get
import java.util.*

class SplashViewModel : BaseViewModel() {

    val success: MutableLiveData<Options> = MutableLiveData()

    fun initApp() {
        launch({
            val startsAt = Calendar.getInstance().timeInMillis

            injectCore()

            val preferences = get<AppPreferences>()

            val endsAt = Calendar.getInstance().timeInMillis
            val diff = endsAt - startsAt

            if (diff < DELAY) {
                Thread.sleep(DELAY - diff)
            }

            if (preferences.getBoolean(PrefKeys.APP_FIRST_INIT)) {
                success.postValue(Options(true))
            } else {
                success.postValue(Options(false))
            }
        }, { e ->
            onError(e)
        })
    }

    data class Options(val firstInit: Boolean)

    companion object {

        const val DELAY = 1000L
    }
}
