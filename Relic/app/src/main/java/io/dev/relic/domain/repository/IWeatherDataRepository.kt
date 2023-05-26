package io.dev.relic.domain.repository

import io.dev.relic.core.data.network.api.dto.weather.WeatherApiDTO
import io.dev.relic.domain.model.NetworkResult

/**
 * @see io.dev.relic.core.repository.WeatherDataRepositoryImpl
 * */
interface IWeatherDataRepository {

    /**
     * Fetch the latest weather info data from Remote-server.
     *
     * @param latitude
     * @param longitude
     * */
    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherApiDTO>

}