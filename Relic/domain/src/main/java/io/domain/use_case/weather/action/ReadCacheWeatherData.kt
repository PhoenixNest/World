package io.domain.use_case.weather.action

import io.data.entity.WeatherEntity
import io.core.database.repository.RelicDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCacheWeatherData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    operator fun invoke(): Flow<List<WeatherEntity>> {
        return databaseRepository.readWeatherDataCache()
    }
}