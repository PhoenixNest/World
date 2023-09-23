package io.dev.relic.domain.use_case.weather.action

import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.repository.IWeatherDataRepository
import kotlinx.coroutines.CoroutineScope
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