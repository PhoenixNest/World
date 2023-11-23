package io.module.core.network.monitor

import kotlinx.coroutines.flow.Flow

interface INetworkMonitor {
    fun observe(): Flow<NetworkStatus>
}