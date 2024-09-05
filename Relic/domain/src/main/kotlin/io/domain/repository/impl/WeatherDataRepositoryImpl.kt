package io.domain.repository.impl

import io.core.network.api.IWeatherApi
import io.data.dto.weather.WeatherForecastDTO
import io.data.model.NetworkResult
import io.domain.repository.IWeatherDataRepository
import javax.inject.Inject

/**
 * @see IWeatherDataRepository
 * */
class WeatherDataRepositoryImpl @Inject constructor(
    private val weatherApi: IWeatherApi
) : IWeatherDataRepository {

    private var weatherForecastResult: NetworkResult<WeatherForecastDTO> = NetworkResult.Loading()

    companion object {
        private const val TAG = "WeatherDataRepository"
    }

    /**
     * Fetch the latest weather info data from Remote-server.
     *
     * @param latitude
     * @param longitude
     * */
    override suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherForecastDTO> {
        weatherForecastResult = try {
            val data = weatherApi.getWeatherData(
                latitude = latitude,
                longitude = longitude
            )

            NetworkResult.Success(data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }

        return weatherForecastResult
    }
}