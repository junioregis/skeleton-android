package com.domain.skeleton.core.exception.keystore

class KeyStoreDecodeException(t: Throwable? = null)
    : KeyStoreException("Decode failed", t)