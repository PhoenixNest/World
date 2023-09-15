package io.dev.relic.domain.use_case.weather.action

import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.core.data.network.mappers.WeatherDataMapper.toWeatherInfoModel
import io.dev.relic.core.data.network.monitor.FetchStatus
import io.dev.relic.core.data.network.monitor.IFetchDataMonitor
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.repository.IWeatherDataRepository
import io.dev.relic.domain.use_case.weather.TAG
import io.dev.relic.global.utils.LogUtil
import javax.inject.Inject

class FetchWeatherData @Inject constructor(
    private val weatherDataRepository: IWeatherDataRepository
) {
    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        listener: IFetchDataMonitor
    ): Pair<FetchStatus, WeatherInfoModel?> {
        LogUtil.verbose(TAG, "[WeatherApi] Start requesting data.")
        var fetchStatus: Pair<FetchStatus, WeatherInfoModel?> = Pair(FetchStatus.FETCHING, null)
        listener.onFetching()

        // Fetch the latest data from remote-server.
        val result: NetworkResult<WeatherForecastDTO> = weatherDataRepository.getWeatherData(
            latitude = latitude,
            longitude = longitude
        )

        // Handle server result.
        when (result) {
            is NetworkResult.Success -> {
                if (result.data == null) {
                    // In fact, sometimes the request succeeds, but the server returns empty data.
                    val errorMessage = "Server error, retry it after."
                    LogUtil.debug(TAG, "[WeatherApi] $errorMessage")
                    fetchStatus = Pair(FetchStatus.FETCH_SUCCEED, null)
                    listener.onFetchSucceedButNoData(errorMessage = errorMessage)
                } else {
                    LogUtil.apply {
                        debug(TAG, "[WeatherApi] Loading data succeeded.")
                        debug(TAG, "[WeatherApi] Datasource: ${result.data}")
                    }
                    fetchStatus = Pair(FetchStatus.FETCH_SUCCEED, result.data.toWeatherInfoModel())
                    listener.onFetchSucceed(dto = result.data)
                }
            }

            is NetworkResult.Failed -> {
                LogUtil.apply {
                    error(TAG, "[WeatherApi] Failed to load data.")
                    error(TAG, "[WeatherApi] Error message: ${result.message}")
                }
                fetchStatus = Pair(FetchStatus.FETCH_FAILED, null)
                listener.onFetchFailed(
                    errorCode = result.code,
                    errorMessage = result.message
                )
            }
        }

        return fetchStatus
    }
}