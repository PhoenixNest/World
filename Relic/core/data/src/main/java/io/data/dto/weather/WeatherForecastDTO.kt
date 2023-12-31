package io.data.dto.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherForecastDTO(
    @Json(name = "hourly_units")
    val weatherUnitDataDTO: WeatherUnitDataDTO?,
    @Json(name = "hourly")
    val weatherHourlyDTO: WeatherHourlyDTO?
)

@JsonClass(generateAdapter = true)
data class WeatherUnitDataDTO(
    @Json(name = "time")
    val time: String?,
    @Json(name = "temperature_2m")
    val temperature: String?,
    @Json(name = "relativehumidity_2m")
    val humidity: String?,
    @Json(name = "weathercode")
    val weatherCode: String?,
    @Json(name = "surface_pressure")
    val pressure: String?,
    @Json(name = "windspeed_10m")
    val windSpeed: String?
)

@JsonClass(generateAdapter = true)
data class WeatherHourlyDTO(
    @Json(name = "time")
    val times: List<String?>?,
    @Json(name = "temperature_2m")
    val temperatures: List<Double?>?,
    @Json(name = "relativehumidity_2m")
    val humidity: List<Int?>?,
    @Json(name = "weathercode")
    val weatherCode: List<Int?>?,
    @Json(name = "surface_pressure")
    val pressures: List<Double?>?,
    @Json(name = "windspeed_10m")
    val winSpeeds: List<Double?>?,
    @Json(name = "is_day")
    val isDay: List<Int?>?,
)
