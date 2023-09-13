package io.dev.relic.core.data.network.monitor

interface IFetchDataMonitor {

    fun onFetching()

    fun <T> onFetchSucceed(dto: T)

    fun onFetchSucceedButNoData(errorMessage: String)

    fun onFetchFailed(
        errorCode: Int?,
        errorMessage: String?
    )

}