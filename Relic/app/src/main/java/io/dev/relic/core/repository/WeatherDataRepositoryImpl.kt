package io.dev.relic.core.repository

import io.dev.relic.core.module.data.network.api.IWeatherApi
import io.dev.relic.core.module.data.network.mappers.WeatherDataMapper.toWeatherInfoModel
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.model.weather.WeatherInfoModel
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
    ): NetworkResult<WeatherInfoModel> {
        return try {
            NetworkResult.Success(
                data = weatherApi.getWeatherData(
                    latitude = latitude,
                    longitude = longitude
                ).toWeatherInfoModel()
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }
    }

}