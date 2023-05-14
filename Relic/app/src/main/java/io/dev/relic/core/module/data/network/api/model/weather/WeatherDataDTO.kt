package io.dev.relic.core.module.data.network.api.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDataDTO(
    @field:Json(name = "time")
    val times: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "weathercode")
    val weatherCode: List<Int>,
    @field:Json(name = "relativehumidity_2m")
    val humidity: List<Int>,
    @field:Json(name = "windspeed_10m")
    val winSpeeds: List<Double>,
    @field:Json(name = "pressure_msl")
    val pressures: List<Double>
)
