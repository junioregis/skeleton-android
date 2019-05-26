package com.domain.skeleton.auth.di

import com.domain.skeleton.auth.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(authModule)
}

private val authModule = module {
    viewModel { AuthViewModel(get(), get()) }
}