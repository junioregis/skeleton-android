package com.domain.skeleton.core.di.modules

import android.content.Context
import com.domain.skeleton.core.helper.ToastHelper
import com.domain.skeleton.core.view.AppViews
import org.koin.dsl.module

val viewModule = module {
    single { ToastHelper(get()) }

    factory { (context: Context) -> AppViews(context) }
}