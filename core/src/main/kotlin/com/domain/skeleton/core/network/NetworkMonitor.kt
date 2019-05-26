package com.domain.skeleton.core.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.lifecycle.LiveData
import com.domain.skeleton.core.R

class NetworkMonitor(private val context: Context) : LiveData<NetworkMonitor.Info>() {

    private val manager: ConnectivityManager
        get() {
            return context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        }

    val isNetworkAvailable: Boolean
        get() {
            return status == Status.CONNECTED
        }

    val isOnline: Boolean
        get() {
            return try {
                val runtime = Runtime.getRuntime()
                val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
                val exitValue = ipProcess.waitFor()

                exitValue == 0
            } catch (e: Exception) {
                false
            }
        }

    val status: Status
        get() {
            return when (type) {
                Type.WIFI, Type.MOBILE -> Status.CONNECTED
                else -> Status.DISCONNECTED
            }
        }

    @Suppress("DEPRECATION")
    val type: Type
        get() {
            return if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val capabilities = manager.getNetworkCapabilities(manager.activeNetwork)

                when {
                    capabilities == null -> Type.NONE
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> Type.WIFI
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> Type.MOBILE
                    else -> Type.NONE
                }
            } else {
                val type = manager.activeNetworkInfo?.type

                when (type) {
                    ConnectivityManager.TYPE_WIFI -> Type.WIFI
                    ConnectivityManager.TYPE_MOBILE -> Type.MOBILE
                    else -> Type.NONE
                }
            }
        }

    private val callback = object : ConnectivityManager.NetworkCallback() {

        override fun onLost(network: Network?) {
            super.onLost(network)

            val info = Info(Status.DISCONNECTED, type)

            postValue(info)
        }

        override fun onAvailable(network: Network?) {
            super.onAvailable(network)

            val info = Info(Status.CONNECTED, type)

            postValue(info)
        }
    }

    override fun onActive() {
        super.onActive()

        val info = if (status == Status.CONNECTED) {
            Info(Status.CONNECTED, type)
        } else {
            Info(Status.DISCONNECTED, type)
        }

        postValue(info)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            manager.registerDefaultNetworkCallback(callback)
        } else {
            val networkRequest = NetworkRequest.Builder().build()
            manager.registerNetworkCallback(networkRequest, callback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        manager.unregisterNetworkCallback(callback)
    }

    data class Info(val status: Status, val type: Type)

    enum class Status(val value: Int) {

        CONNECTED(R.string.msg_connected),
        DISCONNECTED(R.string.msg_disconnected)
    }

    enum class Type {

        NONE, WIFI, MOBILE
    }
}