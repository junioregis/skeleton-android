package com.domain.skeleton.core.repository

import com.domain.skeleton.core.AppConfig
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class AppRepository<T> : KoinComponent {

    protected val config: AppConfig by inject()

    protected abstract val datasource: T
}