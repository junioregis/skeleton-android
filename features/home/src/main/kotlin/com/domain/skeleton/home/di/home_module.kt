package com.domain.skeleton.home.di

import com.domain.skeleton.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(homeModule)
}

private val homeModule = module {
    viewModel { HomeViewModel(get()) }
}
