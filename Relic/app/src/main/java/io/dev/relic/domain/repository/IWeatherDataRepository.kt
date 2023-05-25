package io.dev.relic.domain.repository

import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.model.weather.WeatherInfoModel

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
    ): NetworkResult<WeatherInfoModel>

}