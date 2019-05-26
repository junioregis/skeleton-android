package com.domain.skeleton.core.exception.social

abstract class SocialException(message: String? = null, t: Throwable? = null)
    : RuntimeException(message, t)
