package io.dev.relic.domain.model.weather

import java.time.LocalDateTime

/**
 * @see io.dev.relic.core.data.network.mappers.WeatherDataMapper
 * */
data class WeatherDataModel(
    val time: LocalDateTime,
    val temperature: Double,
    val humidity: Int,
    val weatherCode: Int,
    val pressure: Double,
    val windSpeed: Double,
    val isDay: Boolean
)
