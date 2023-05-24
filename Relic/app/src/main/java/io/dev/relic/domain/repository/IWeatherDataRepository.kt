package io.dev.relic.domain.repository

import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.model.weather.WeatherInfoModel

/**
 * @see io.dev.relic.core.repository.WeatherDataRepositoryImpl
 * */
interface IWeatherDataRepository {

    suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherInfoModel>

}