package io.dev.relic.core.module.data.network.api.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherUnitDataDTO(
    @field:Json(name = "time")
    val time: String,
    @field:Json(name = "temperature_2m")
    val temperature: String,
    @field:Json(name = "weathercode")
    val weatherCode: String,
    @field:Json(name = "relativehumidity_2m")
    val humidity: String,
    @field:Json(name = "windspeed_10m")
    val windSpeed: String,
    @field:Json(name = "pressure_msl")
    val pressure: String
)