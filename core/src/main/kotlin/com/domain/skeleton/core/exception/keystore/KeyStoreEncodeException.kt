package com.domain.skeleton.core.exception.keystore

class KeyStoreEncodeException(t: Throwable? = null)
    : KeyStoreException("Encode failed", t)