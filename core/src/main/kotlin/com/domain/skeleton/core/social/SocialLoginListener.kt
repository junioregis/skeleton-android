package com.domain.skeleton.core.social

interface SocialLoginListener {

    fun onSocialSuccess(user: User)

    fun onSocialError(t: Throwable)
}