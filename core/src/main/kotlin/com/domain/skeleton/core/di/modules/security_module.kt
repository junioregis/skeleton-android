package com.domain.skeleton.core.di.modules

import com.domain.skeleton.core.security.AppKeyStore
import org.koin.dsl.module

val securityModule = module {
    single {
        val keyStore = AppKeyStore(get())

        if (!keyStore.exists()) {
            keyStore.generate()
        }

        keyStore
    }
}