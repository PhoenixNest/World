package io.domain.use_case.weather.action

import io.core.database.repository.RelicDatabaseRepository
import io.data.entity.weather.WeatherEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QueryWeatherData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    operator fun invoke(): Flow<List<WeatherEntity>> {
        return databaseRepository.queryWeatherData()
    }
}