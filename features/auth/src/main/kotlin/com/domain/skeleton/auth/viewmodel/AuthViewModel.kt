package com.domain.skeleton.auth.viewmodel

import android.content.Intent
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.domain.skeleton.core.exception.network.AuthException
import com.domain.skeleton.core.preference.AppPreferences
import com.domain.skeleton.core.preference.PrefKeys
import com.domain.skeleton.core.repository.AuthRepository
import com.domain.skeleton.core.social.Providers
import com.domain.skeleton.core.social.SocialLoginListener
import com.domain.skeleton.core.social.User
import com.domain.skeleton.core.social.provider.FacebookLogin
import com.domain.skeleton.core.social.provider.GoogleLogin
import com.domain.skeleton.core.view.social.FacebookLoginButton
import com.domain.skeleton.core.view.social.GoogleLoginButton
import com.domain.skeleton.core.viewmodel.BaseViewModel

class AuthViewModel(private val preferences: AppPreferences,
                    private val authRepository: AuthRepository) : BaseViewModel() {

    private val google = GoogleLogin()
    private val facebook = FacebookLogin()

    val login = MutableLiveData<User>()

    fun setFacebookButton(button: FacebookLoginButton, onClick: () -> Unit) {
        facebook.setButton(button, onClick, object : SocialLoginListener {

            override fun onSocialSuccess(user: User) = doLogin(user)

            override fun onSocialError(t: Throwable) = onError(AuthException(Providers.FACEBOOK))
        })
    }

    fun setGoogleButton(button: GoogleLoginButton, onClick: () -> Unit) {
        google.setButton(button, onClick)
    }

    fun setLoginLaterButton(view: View, onClick: () -> Unit) {
        view.setOnClickListener {
            preferences.set(PrefKeys.APP_FIRST_INIT, false)
            onClick.invoke()
        }
    }

    fun handleResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            facebook.requestCode -> facebook.onActivityResult(resultCode, data)
            google.requestCode -> google.onActivityResult(data, object : SocialLoginListener {

                override fun onSocialSuccess(user: User) = doLogin(user)

                override fun onSocialError(t: Throwable) = onError(AuthException(Providers.GOOGLE))
            })
        }
    }

    private fun doLogin(user: User) {
        launch({
            authRepository.getToken(user)
            preferences.set(PrefKeys.APP_FIRST_INIT, false)
            login.postValue(user)
        }, { e ->
            preferences.reset()

            when (user.provider) {
                Providers.GOOGLE -> {
                    google.signOut()
                }
                Providers.FACEBOOK -> {
                    facebook.signOut()
                }
            }

            onError(e)
        })
    }
}
