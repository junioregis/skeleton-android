package com.domain.skeleton.di

import com.domain.skeleton.viewmodel.MainViewModel
import com.domain.skeleton.viewmodel.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectSplash() = loadSplash

fun injectApp() = loadApp

private val loadSplash by lazy {
    loadKoinModules(splashModule)
}

private val loadApp by lazy {
    loadKoinModules(appModule)
}

private val splashModule = module {
    viewModel { SplashViewModel() }
}

private val appModule = module {
    viewModel { MainViewModel(get()) }
}

