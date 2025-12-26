package com.newsandfeed.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

actual class ConnectivityObserver(
    private val context: Context
) {

    actual fun observe(): Flow<NetWorkStatus> = callbackFlow {
        val connectiveManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val callback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                launch { send(NetWorkStatus.Available) }
            }

            override fun onLost(network: Network) {
                launch { send(NetWorkStatus.Unavailable) }
            }

            override fun onLosing(network: Network, maxMsToLive: Int) {
                launch { send(NetWorkStatus.Unavailable) }
            }

            override fun onUnavailable() {
                launch { send(NetWorkStatus.Unavailable) }
            }

        }

        connectiveManager.registerDefaultNetworkCallback(callback)

        val isConnected = connectiveManager.activeNetwork != null
        launch { send(if (isConnected) NetWorkStatus.Available else NetWorkStatus.Unavailable) }

        awaitClose {
            connectiveManager.unregisterNetworkCallback(callback)
        }

    }.distinctUntilChanged()


}