package com.domain.skeleton.core.social.provider

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.domain.skeleton.core.exception.social.SocialAccountNotFoundException
import com.domain.skeleton.core.exception.social.SocialButtonException
import com.domain.skeleton.core.exception.social.SocialFetchUserException
import com.domain.skeleton.core.exception.social.SocialFieldNotFoundException
import com.domain.skeleton.core.social.Providers
import com.domain.skeleton.core.social.SocialLoginListener
import com.domain.skeleton.core.social.User
import com.domain.skeleton.core.view.social.GoogleLoginButton
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class GoogleLogin : IProvider, KoinComponent {

    private val context: Context by inject()

    private val EMAIL_SCOPE = Scope("https://www.googleapis.com/auth/userinfo.email")
    private val PROFILE_SCOPE = Scope("https://www.googleapis.com/auth/userinfo.profile")
    private val BIRTHDAY_SCOPE = Scope("https://www.googleapis.com/auth/user.birthday.read")

    val requestCode = 4664

    override val isLoggedIn: Boolean
        get() {
            return GoogleSignIn.getLastSignedInAccount(context) != null
        }

    private val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestId()
            .requestEmail()
            .requestScopes(EMAIL_SCOPE)
            .requestScopes(PROFILE_SCOPE)
            .requestScopes(BIRTHDAY_SCOPE)
            .build()

    fun setButton(view: GoogleLoginButton, onClick: () -> Unit) {
        if (isLoggedIn) {
            signOut()
        }

        val activity = view.context as? Activity ?: throw SocialButtonException()
        val client = GoogleSignIn.getClient(activity, options)

        view.button.setOnClickListener {
            onClick.invoke()
            activity.startActivityForResult(client.signInIntent, requestCode)
        }
    }

    @Throws(SocialAccountNotFoundException::class,
            SocialFetchUserException::class)
    fun onActivityResult(data: Intent?, listener: SocialLoginListener) {
        CoroutineScope(Dispatchers.IO).launch {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account = task.getResult(ApiException::class.java)

                if (account != null) {
                    val scopes = arrayOf(EMAIL_SCOPE, PROFILE_SCOPE, BIRTHDAY_SCOPE)
                    val accessToken = GoogleAuthUtil.getToken(context, account.account, "oauth2:${scopes.joinToString(" ")}")

                    val user = User(Providers.GOOGLE,
                            accessToken,
                            account.id ?: throw SocialFieldNotFoundException("ID"),
                            account.email ?: throw SocialFieldNotFoundException("EMAIL"),
                            account.displayName
                                    ?: throw SocialFieldNotFoundException("DISPLAY_NAME"),
                            account.photoUrl?.toString()
                                    ?: throw SocialFieldNotFoundException("PHOTO_URL"))

                    listener.onSocialSuccess(user)
                } else {
                    signOut()
                    listener.onSocialError(SocialAccountNotFoundException())
                }
            } catch (e: Exception) {
                signOut()
                listener.onSocialError(e)
            }
        }
    }

    override fun signOut() {
        GoogleSignIn.getClient(context, options).signOut()
    }
}