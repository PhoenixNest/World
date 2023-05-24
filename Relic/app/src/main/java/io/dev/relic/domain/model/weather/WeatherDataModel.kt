package io.dev.relic.domain.model.weather

import java.time.LocalDateTime

data class WeatherDataModel(
    val time: LocalDateTime,
    val temperature: Double,
    val weatherCode: Int,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Double
)
