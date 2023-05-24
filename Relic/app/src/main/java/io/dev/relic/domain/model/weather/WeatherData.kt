package io.dev.relic.domain.model.weather

data class WeatherData(
    val time: String,
    val temperature: Double,
    val weatherCode: Int,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Double
)