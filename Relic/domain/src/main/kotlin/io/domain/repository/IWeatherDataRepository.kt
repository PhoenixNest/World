package io.domain.repository

import io.data.dto.weather.WeatherForecastDTO
import io.data.model.NetworkResult
import io.domain.repository.impl.WeatherDataRepositoryImpl

/**
 * @see WeatherDataRepositoryImpl
 * */
interface IWeatherDataRepository {

    /**
     * Fetch the latest weather info data from Remote-server.
     *
     * @param latitude
     * @param longitude
     * */
    suspend fun getWeatherForecast(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherForecastDTO>

}