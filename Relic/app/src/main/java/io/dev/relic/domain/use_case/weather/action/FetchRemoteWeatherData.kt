package io.dev.relic.domain.use_case.weather.action

import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.repository.IWeatherDataRepository
import io.dev.relic.domain.use_case.weather.TAG
import io.dev.relic.global.utils.LogUtil
import javax.inject.Inject

class FetchRemoteWeatherData @Inject constructor(
    private val weatherDataRepository: IWeatherDataRepository
) {

    interface IWeatherListener {

        fun onFetching()

        fun onFetchSucceed(weatherForecastDTO: WeatherForecastDTO)

        fun onFetchSucceedButNoData(errorMessage: String)

        fun onFetchFailed(errorMessage: String?)

    }

    suspend operator fun invoke(
        latitude: Double,
        longitude: Double,
        listener: IWeatherListener
    ) {

        LogUtil.verbose(TAG, "[WeatherApi] Start requesting weather data.")
        listener.onFetching()

        // Fetch the latest weather data from remote-server.
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
                    listener.onFetchSucceedButNoData(errorMessage = errorMessage)

                } else {
                    LogUtil.apply {
                        debug(TAG, "[WeatherApi] Loading weather data succeeded.")
                        debug(TAG, "[WeatherApi] Datasource: ${result.data}")
                    }
                    listener.onFetchSucceed(weatherForecastDTO = result.data)
                }
            }

            is NetworkResult.Failed -> {
                LogUtil.apply {
                    error(TAG, "[WeatherApi] Failed to load weather data.")
                    error(TAG, "[WeatherApi] Error message: ${result.message}")
                }
                listener.onFetchFailed(errorMessage = result.message)
            }
        }
    }

}