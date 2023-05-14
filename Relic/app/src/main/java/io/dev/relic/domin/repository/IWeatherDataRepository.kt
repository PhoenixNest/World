package io.dev.relic.domin.repository

import io.dev.relic.core.module.data.network.api.model.weather.WeatherDTO
import io.dev.relic.domin.model.NetworkResult

interface IWeatherDataRepository {

    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherDTO>

}