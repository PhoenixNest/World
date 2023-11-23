package io.dev.relic.domain.use_case.weather.action

import io.data.model.NetworkResult
import io.data.dto.weather.WeatherForecastDTO
import io.dev.relic.domain.repository.IWeatherDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchWeatherData @Inject constructor(
    private val weatherDataRepository: IWeatherDataRepository
) {
    operator fun invoke(
        latitude: Double,
        longitude: Double
    ): Flow<NetworkResult<WeatherForecastDTO>> {
        return flow {
            // Fetch the latest data from remote-server.
            emit(weatherDataRepository.getWeatherData(latitude, longitude))
        }.flowOn(Dispatchers.IO)
    }
}