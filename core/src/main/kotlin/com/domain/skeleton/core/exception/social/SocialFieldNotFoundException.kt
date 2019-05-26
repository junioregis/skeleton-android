package com.domain.skeleton.core.exception.social

class SocialFieldNotFoundException(field: String)
    : SocialException("Field \"$field\" not found")