package com.domain.skeleton.core.exception.keystore

class KeyStoreGenerateException(t: Throwable? = null)
    : KeyStoreException("Generation failed", t)