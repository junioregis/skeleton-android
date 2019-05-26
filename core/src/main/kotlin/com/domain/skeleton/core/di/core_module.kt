package com.domain.skeleton.core.di

import com.domain.skeleton.core.di.modules.*
import org.koin.core.context.loadKoinModules

fun injectCore() = loadCore

private val loadCore by lazy {
    loadKoinModules(*coreModules)
}

private val coreModules = arrayOf(
        authModule,
        configModule,
        databaseModule,
        navModule,
        networkModule,
        preferencesModule,
        repositoryModule,
        securityModule,
        viewModule)
