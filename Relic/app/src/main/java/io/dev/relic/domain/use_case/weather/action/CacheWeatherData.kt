package io.dev.relic.domain.use_case.weather.action

import io.dev.relic.core.data.database.entity.WeatherEntity
import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import javax.inject.Inject

class CacheWeatherData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {

    suspend operator fun invoke(weatherEntity: WeatherEntity) {
        databaseRepository.insertWeatherData(weatherEntity)
    }

}