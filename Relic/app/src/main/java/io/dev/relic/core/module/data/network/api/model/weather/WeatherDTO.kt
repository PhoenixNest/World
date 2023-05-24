package io.dev.relic.core.module.data.network.api.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Json data sample:
 * ```
 * {
 *      "latitude": 49.9375,
 *      "longitude": 50.0,
 *      "generationtime_ms": 3.448009490966797,
 *      "utc_offset_seconds": 0,
 *      "timezone": "GMT",
 *      "timezone_abbreviation": "GMT",
 *      "elevation": 6.0,
 *      "hourly_units": {
 *          "time": "iso8601",
 *          "temperature_2m": "°C",
 *          "weathercode": "wmo code",
 *          "relativehumidity_2m": "%",
 *          "windspeed_10m": "km/h",
 *          "pressure_msl": "hPa"
 *      },
 *      "hourly": {
 *          "time": [
 *              "2023-05-24T00:00",
 *              "2023-05-24T01:00",
 *              ...
 *          ],
 *          "temperature_2m": [
 *              19.5,
 *              19.1,
 *              ...
 *          ],
 *          "weathercode": [
 *              2,
 *              2,
 *              ...
 *          ],
 *          "relativehumidity_2m": [
 *              37,
 *              37,
 *              ...
 *          ]
 *      }
 *  }
 * ```
 *
 * @see io.dev.relic.core.module.data.network.mappers.WeatherDataMapper
 * @see io.dev.relic.domain.model.weather.WeatherDataModel
 * @see WeatherUnitDataDTO
 * @see WeatherDataDTO
 * */
@JsonClass(generateAdapter = true)
data class WeatherDTO(
    @field:Json(name = "hourly_units")
    val weatherUnitData: WeatherUnitDataDTO,
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDTO
)

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