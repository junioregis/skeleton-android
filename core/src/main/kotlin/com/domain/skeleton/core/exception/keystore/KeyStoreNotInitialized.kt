package com.domain.skeleton.core.exception.keystore

class KeyStoreNotInitialized(alias: String, t: Throwable? = null)
    : KeyStoreException("KeyStore not initialized with alias \"$alias\"", t)