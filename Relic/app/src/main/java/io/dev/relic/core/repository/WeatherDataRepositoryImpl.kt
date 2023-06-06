package io.dev.relic.core.repository

import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import io.dev.relic.core.data.network.api.IWeatherApi
import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.core.data.network.mappers.WeatherDataMapper.toWeatherEntity
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.repository.IWeatherDataRepository
import javax.inject.Inject

/**
 * @see io.dev.relic.domain.repository.IWeatherDataRepository
 * */
class WeatherDataRepositoryImpl @Inject constructor(
    private val weatherApi: IWeatherApi,
    private val databaseRepository: RelicDatabaseRepository
) : IWeatherDataRepository {

    companion object {

        private const val TAG = "WeatherDataRepository"

    }

    /**
     * Fetch the latest weather info data from Remote-server.
     *
     * @param latitude
     * @param longitude
     * */
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherForecastDTO> {
        return try {
            val weatherApiDTO: WeatherForecastDTO = weatherApi.getWeatherData(
                latitude = latitude,
                longitude = longitude
            )

            // Always save the latest weather information data to the database.
            databaseRepository.insertWeatherData(weatherEntity = weatherApiDTO.toWeatherEntity())

            NetworkResult.Success(data = weatherApiDTO)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }
    }

}