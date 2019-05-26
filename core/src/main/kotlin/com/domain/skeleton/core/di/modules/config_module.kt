package com.domain.skeleton.core.di.modules

import com.domain.skeleton.core.AppConfig
import org.koin.dsl.module

val configModule = module {
    single { AppConfig(get(), get(), get(), getProperty("SERVER_URL")) }
}