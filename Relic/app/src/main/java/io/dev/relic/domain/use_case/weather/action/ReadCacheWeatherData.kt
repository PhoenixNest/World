package io.dev.relic.domain.use_case.weather.action

import io.dev.relic.core.data.database.entity.WeatherEntity
import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadCacheWeatherData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    operator fun invoke(): Flow<List<WeatherEntity>> {
        return databaseRepository.readWeatherDataCache()
    }
}