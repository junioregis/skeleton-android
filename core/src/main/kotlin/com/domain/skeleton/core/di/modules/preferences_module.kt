package com.domain.skeleton.core.di.modules

import com.domain.skeleton.core.preference.AppPreferences
import org.koin.dsl.module

val preferencesModule = module {
    single { AppPreferences(get(), get(), 1) }
}