package com.domain.skeleton.core.di.modules

import com.domain.skeleton.core.network.NetworkMonitor
import com.domain.skeleton.core.network.WebService
import org.koin.dsl.module

val networkModule = module {
    single { NetworkMonitor(get()) }

    single { WebService(get(), get()) }

    single { get<WebService>().auth() }
    single { get<WebService>().api(get()) }
}