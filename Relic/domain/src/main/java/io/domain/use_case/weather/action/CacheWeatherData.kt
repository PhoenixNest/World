package io.domain.use_case.weather.action

import io.core.database.repository.RelicDatabaseRepository
import io.data.entity.weather.WeatherEntity
import javax.inject.Inject

class CacheWeatherData @Inject constructor(
    private val databaseRepository: RelicDatabaseRepository
) {
    suspend operator fun invoke(weatherEntity: WeatherEntity) {
        databaseRepository.insertWeatherData(weatherEntity)
    }
}