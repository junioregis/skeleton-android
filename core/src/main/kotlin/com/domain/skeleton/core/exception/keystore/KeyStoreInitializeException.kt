package com.domain.skeleton.core.exception.keystore

class KeyStoreInitializeException(t: Throwable? = null)
    : KeyStoreException("Initialize error", t)