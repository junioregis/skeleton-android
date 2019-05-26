package com.domain.skeleton.core

import android.content.Context
import android.os.Build
import com.domain.skeleton.core.datasource.local.AppDatabase
import com.domain.skeleton.core.preference.AppPreferences
import java.util.*

class AppConfig(private val context: Context,
                val preferences: AppPreferences,
                val database: AppDatabase,
                val serverUrl: String) {

    @Suppress("DEPRECATION")
    val locale: Locale
        get() {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                context.resources.configuration.locales.get(0)
            } else {
                context.resources.configuration.locale
            }
        }

    fun cleanApp() {
        preferences.reset()
        database.reset()
    }
}