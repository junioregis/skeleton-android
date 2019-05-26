package com.domain.skeleton.core.exception.keystore

abstract class KeyStoreException(message: String? = null, t: Throwable? = null)
    : Exception(message, t)