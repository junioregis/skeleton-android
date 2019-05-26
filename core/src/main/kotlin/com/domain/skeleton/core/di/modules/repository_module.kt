package com.domain.skeleton.core.di.modules

import com.domain.skeleton.core.repository.AuthRepository
import com.domain.skeleton.core.repository.MeRepository
import com.domain.skeleton.core.repository.ServerRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepository(get()) }
    single { ServerRepository() }
    single { MeRepository(get()) }
}