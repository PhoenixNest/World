package io.dev.relic.core.module.data.network.api.model.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDTO(
    @field:Json(name = "hourly_units")
    val weatherUnitData: WeatherUnitDataDTO,
    @field:Json(name = "hourly")
    val weatherData: WeatherDataDTO
)
