package io.dev.relic.core.repository

import io.dev.relic.core.module.data.network.api.IWeatherApi
import io.dev.relic.core.module.data.network.api.model.weather.WeatherDTO
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.repository.IWeatherDataRepository
import javax.inject.Inject

/**
 * @see io.dev.relic.domain.repository.IWeatherDataRepository
 * */
class WeatherDataRepositoryImpl @Inject constructor(
    private val weatherApi: IWeatherApi
) : IWeatherDataRepository {

    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherDTO> {
        return try {
            NetworkResult.Success(data = weatherApi.getWeatherData(latitude, longitude))
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }
    }

}