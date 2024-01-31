package io.domain.use_case.weather.action

import io.data.dto.weather.WeatherForecastDTO
import io.data.model.NetworkResult
import io.domain.repository.IWeatherDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchWeatherData @Inject constructor(
    private val weatherDataRepository: IWeatherDataRepository
) {

    /**
     * Fetch the latest weather info data from Remote-server.
     *
     * @param latitude
     * @param longitude
     * */
    operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Flow<NetworkResult<WeatherForecastDTO>> {
        return flow {
            // Fetch the latest data from remote-server.
            emit(weatherDataRepository.getWeatherForecast(latitude, longitude))
        }.flowOn(Dispatchers.IO)
    }
}