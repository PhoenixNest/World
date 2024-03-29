package io.domain.use_case.weather.action

import io.data.entity.WeatherEntity
import io.core.database.repository.RelicDatabaseRepository
import javax.inject.Inject

class CacheWeatherData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    suspend operator fun invoke(weatherEntity: WeatherEntity) {
        databaseRepository.insertWeatherData(weatherEntity)
    }
}