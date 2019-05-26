package com.domain.skeleton.core.di.modules

import com.domain.skeleton.core.navigation.Navigator
import org.koin.dsl.module

val navModule = module {
    single { Navigator(get()) }
}
