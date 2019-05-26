package com.domain.skeleton.core.di.modules

import com.domain.skeleton.core.auth.UserCredentials
import org.koin.dsl.module

val authModule = module {
    single { UserCredentials(get()) }
}
