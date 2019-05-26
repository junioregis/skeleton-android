package com.domain.skeleton.core.view

import android.content.Context
import org.koin.core.context.GlobalContext

class AppViews(private val context: Context?) {

    private val safeContext: Context
        get() {
            return context ?: GlobalContext.get().koin.get()
        }

    val toolbar: ToolbarView by lazy {
        ToolbarView(safeContext)
    }

    val loading: LoadingView by lazy {
        LoadingView(safeContext)
    }

    val unauthorized: UnauthorizedView by lazy {
        UnauthorizedView(safeContext)
    }

    val noConnection: NoConnectionView by lazy {
        NoConnectionView(safeContext)
    }

    val customError: CustomErrorView by lazy {
        CustomErrorView(safeContext)
    }
}