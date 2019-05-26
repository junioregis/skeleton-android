package com.domain.skeleton.core.exception.social

class SocialFetchUserException(t: Throwable)
    : SocialException("Fetch user error", t)