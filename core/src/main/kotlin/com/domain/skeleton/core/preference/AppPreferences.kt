package com.domain.skeleton.core.preference

import android.content.Context
import android.preference.PreferenceManager
import com.domain.skeleton.core.security.AppKeyStore
import com.domain.skeleton.core.util.log
import java.nio.charset.Charset
import kotlin.reflect.KClass

class AppPreferences(context: Context,
                     private val keyStore: AppKeyStore,
                     private val version: Int) {

    private val oldVersion: Int

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

    init {
        oldVersion = getInt(PrefKeys.APP_PREFERENCE_VERSION)

        if (oldVersion < 1) {
            initialize()
        } else if (version > oldVersion) {
            onUpgrade()
        }
    }

    private fun initialize() {
        log.d("initializing preferences")

        addKeys(PrefKeys::class)

        set(PrefKeys.APP_PREFERENCE_VERSION, version)
    }

    fun reset() {
        log.d("resetting preferences")

        val editor = prefs.edit()

        editor.clear()
        editor.apply()

        initialize()
    }

    private fun onUpgrade() {
        log.d("upgrading preferences")

        PrefKeys.values()
                .filterNot { hasKey(it) }
                .forEach { set(it, it.defaultValue) }

    }

    private fun hasKey(key: PrefKeys) = prefs.contains(key.keyName)

    private fun addKeys(clazz: KClass<PrefKeys>) {
        clazz.java.enumConstants
                .filterNot { hasKey(it) }
                .forEach {
                    set(it, it.defaultValue)
                }
    }

    fun set(key: PrefKeys, value: Any) {
        val encrypted = keyStore.encodeToString(value.toString())

        prefs.edit().putString(key.keyName, encrypted).apply()
    }

    private fun get(key: PrefKeys): String {
        val value = prefs.getString(key.keyName, "") ?: ""

        return keyStore.decodeToString(value)
    }

    fun getString(key: PrefKeys): String {
        return if (hasKey(key)) {
            get(key)
        } else {
            key.defaultValue.toString()
        }
    }

    fun getInt(key: PrefKeys): Int {
        return if (hasKey(key)) {
            get(key).toIntOrNull() ?: key.defaultValue.toString().toInt()
        } else {
            key.defaultValue.toString().toInt()
        }
    }

    fun getBoolean(key: PrefKeys): Boolean {
        return if (hasKey(key)) {
            get(key).toBoolean()
        } else {
            key.defaultValue.toString().toBoolean()
        }
    }
}