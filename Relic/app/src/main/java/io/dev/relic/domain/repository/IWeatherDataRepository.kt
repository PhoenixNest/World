package io.dev.relic.domain.repository

import io.dev.relic.core.module.data.network.api.model.weather.WeatherDTO
import io.dev.relic.domain.model.NetworkResult

/**
 * @see io.dev.relic.core.repository.WeatherDataRepositoryImpl
 * */
interface IWeatherDataRepository {

    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherDTO>

}