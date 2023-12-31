package io.domain.repository.impl

import io.data.dto.weather.WeatherForecastDTO
import io.data.mappers.WeatherDataMapper.toWeatherEntity
import io.data.model.NetworkResult
import io.domain.repository.IWeatherDataRepository
import io.core.database.repository.RelicDatabaseRepository
import io.core.network.api.IWeatherApi
import javax.inject.Inject

/**
 * @see IWeatherDataRepository
 * */
class WeatherDataRepositoryImpl @Inject constructor(
    private val weatherApi: IWeatherApi,
    private val databaseRepository: RelicDatabaseRepository
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
    override suspend fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): NetworkResult<WeatherForecastDTO> {
        weatherForecastResult = try {
            val data: WeatherForecastDTO = weatherApi.getWeatherData(latitude, longitude)

            // Always save the latest weather information data to the database.
            databaseRepository.insertWeatherData(weatherEntity = data.toWeatherEntity())
            NetworkResult.Success(data = data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }
        return weatherForecastResult
    }
}