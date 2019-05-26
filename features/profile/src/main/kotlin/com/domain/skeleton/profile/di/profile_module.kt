package com.domain.skeleton.profile.di

import com.domain.skeleton.profile.viewmodel.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(profileModule)
}

private val profileModule = module {
    viewModel { ProfileViewModel(get(), get(), get()) }
}
