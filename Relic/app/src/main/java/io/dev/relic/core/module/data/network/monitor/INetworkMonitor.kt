package io.dev.relic.core.module.data.network.monitor

import kotlinx.coroutines.flow.Flow

interface INetworkMonitor {
    fun observe(): Flow<NetworkStatus>
}