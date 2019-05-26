package com.domain.skeleton.core.exception.network

import com.domain.skeleton.core.social.Providers

class AuthException(val provider: Providers) : Exception()