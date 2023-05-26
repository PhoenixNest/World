package io.dev.relic.core.data.network.api.dto.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Json data sample:
 * ```
 * {
 *     "latitude": 52.52,
 *     "longitude": 13.419998,
 *     "generationtime_ms": 0.33593177795410156,
 *     "utc_offset_seconds": 0,
 *     "timezone": "GMT",
 *     "timezone_abbreviation": "GMT",
 *     "elevation": 38,
 *     "hourly_units": {
 *         "time": "iso8601",
 *         "temperature_2m": "°C",
 *         "relativehumidity_2m": "%",
 *         "weathercode": "wmo code",
 *         "surface_pressure": "hPa",
 *         "windspeed_10m": "km/h",
 *         "is_day": ""
 *     },
 *     "hourly": {
 *         "time": [
 *             "2023-05-26T00:00",
 *             "2023-05-26T01:00",
 *                  ...
 *         ],
 *         "temperature_2m": [
 *             10.7,
 *             10,
 *                  ...
 *         ],
 *         "relativehumidity_2m": [
 *             78,
 *             81,
 *                  ...
 *         ],
 *         "weathercode": [
 *             1,
 *             0,
 *                  ...
 *         ],
 *         "surface_pressure": [
 *             1021.2,
 *             1021.3,
 *                  ...
 *         ],
 *         "windspeed_10m": [
 *             5.8,
 *             4.6,
 *                  ...
 *         ],
 *         "is_day": [
 *             0,
 *             1,
 *                  ...
 *         ]
 *     }
 * }
 * ```
 *
 * @see io.dev.relic.core.data.network.api.IWeatherApi
 * @see io.dev.relic.core.module.data.network.mappers.WeatherDataMapper
 * */
@JsonClass(generateAdapter = true)
data class WeatherApiDTO(
    @field:Json(name = "hourly_units")
    val weatherUnitData: WeatherUnitDataDTO,
    @field:Json(name = "hourly")
    val weatherForecastDTO: WeatherForecastDTO
)

@JsonClass(generateAdapter = true)
data class WeatherUnitDataDTO(
    @field:Json(name = "time")
    val time: String,
    @field:Json(name = "temperature_2m")
    val temperature: String,
    @field:Json(name = "relativehumidity_2m")
    val humidity: String,
    @field:Json(name = "weathercode")
    val weatherCode: String,
    @field:Json(name = "surface_pressure")
    val pressure: String,
    @field:Json(name = "windspeed_10m")
    val windSpeed: String
)

/**
 * @see io.dev.relic.domain.model.weather.WeatherDataModel
 * */
@JsonClass(generateAdapter = true)
data class WeatherForecastDTO(
    @field:Json(name = "time")
    val times: List<String>,
    @field:Json(name = "temperature_2m")
    val temperatures: List<Double>,
    @field:Json(name = "relativehumidity_2m")
    val humidity: List<Int>,
    @field:Json(name = "weathercode")
    val weatherCode: List<Int>,
    @field:Json(name = "surface_pressure")
    val pressures: List<Double>,
    @field:Json(name = "windspeed_10m")
    val winSpeeds: List<Double>,
    @field:Json(name = "is_day")
    val isDay: List<Int>,
)