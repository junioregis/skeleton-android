package com.domain.skeleton.core.social.provider

interface IProvider {

    val isLoggedIn: Boolean

    fun signOut()
}