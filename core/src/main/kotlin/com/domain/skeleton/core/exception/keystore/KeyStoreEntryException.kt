package com.domain.skeleton.core.exception.keystore

class KeyStoreEntryException(t: Throwable? = null)
    : KeyStoreException("Entry error", t)