package io.core.network.monitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

class NetworkMonitor @Inject constructor(
    @ApplicationContext private val context: Context
) : INetworkMonitor {

    private val connectivityManger = context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    override fun observe(): Flow<NetworkStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        send(NetworkStatus.AVAILABLE)
                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        send(NetworkStatus.LOSING)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        send(NetworkStatus.LOST)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        send(NetworkStatus.UNAVAILABLE)
                    }
                }
            }

            connectivityManger.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManger.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}