package io.data.model.weather

import java.time.LocalDateTime

data class WeatherDataModel(
    val time: LocalDateTime?,
    val temperature: Double?,
    val humidity: Int?,
    val weatherCode: Int?,
    val pressure: Double?,
    val windSpeed: Double?,
    val isDay: Boolean?
)
