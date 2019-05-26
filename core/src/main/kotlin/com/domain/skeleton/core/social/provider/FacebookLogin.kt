package com.domain.skeleton.core.social.provider

import android.content.Intent
import android.os.Bundle
import com.domain.skeleton.core.social.Providers
import com.domain.skeleton.core.social.SocialLoginListener
import com.domain.skeleton.core.social.User
import com.domain.skeleton.core.view.social.FacebookLoginButton
import com.facebook.*
import com.facebook.internal.CallbackManagerImpl
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import java.util.*

class FacebookLogin : IProvider {

    private val callbackManager = CallbackManager.Factory.create()

    val requestCode: Int
        get() {
            return CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()
        }

    val accessToken: AccessToken?
        get() {
            return AccessToken.getCurrentAccessToken()
        }

    override val isLoggedIn: Boolean
        get() {
            return !(accessToken?.isExpired ?: true)
        }

    fun setButton(view: FacebookLoginButton, onClick: () -> Unit, listener: SocialLoginListener) {
        if (isLoggedIn) {
            signOut()
        }

        view.button.setOnClickListener { onClick.invoke() }
        view.button.setReadPermissions(Arrays.asList(SCOPE_EMAIL, SCOPE_GENDER, SCOPE_BIRTHDAY))
        view.button.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                fetchUser(result, listener)
            }

            override fun onCancel() {
                signOut()
                listener.onSocialError(FacebookException())
            }

            override fun onError(e: FacebookException) {
                signOut()
                listener.onSocialError(e)
            }
        })
    }

    fun onActivityResult(resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    private fun fetchUser(result: LoginResult, listener: SocialLoginListener) {
        val request = GraphRequest.newMeRequest(result.accessToken) { data, _ ->
            try {
                val picture = let {
                    data.getJSONObject("picture")
                            .getJSONObject("data")
                            .getString("url")
                }

                val user = User(Providers.FACEBOOK,
                        result.accessToken.token,
                        data.get("id").toString(),
                        data.get("email").toString(),
                        data.get("name").toString(),
                        picture)

                listener.onSocialSuccess(user)
            } catch (e: Exception) {
                signOut()
                listener.onSocialError(e)
            }
        }

        val params = Bundle()

        params.putString("fields", "id, email, name, picture.width($AVATAR_SIZE).height($AVATAR_SIZE)")

        request.parameters = params
        request.executeAsync()
    }

    override fun signOut() {
        LoginManager.getInstance().logOut()
    }

    companion object {

        private const val AVATAR_SIZE = 512

        private const val SCOPE_EMAIL = "email"
        private const val SCOPE_GENDER = "user_gender"
        private const val SCOPE_BIRTHDAY = "user_birthday"
    }
}