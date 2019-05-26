package com.domain.skeleton.core.exception.keystore

class KeyStoreDeletionException(alias: String, t: Throwable? = null)
    : KeyStoreException("Failed to delete alias $alias", t)