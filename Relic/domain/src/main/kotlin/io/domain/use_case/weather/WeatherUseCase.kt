package io.domain.use_case.weather

import io.domain.use_case.weather.action.CacheWeatherData
import io.domain.use_case.weather.action.FetchWeatherData
import io.domain.use_case.weather.action.QueryWeatherData

internal const val TAG = "WeatherUnitUseCase"

data class WeatherUseCase(
    val getWeatherData: FetchWeatherData,
    val cacheWeatherData: CacheWeatherData,
    val queryWeatherData: QueryWeatherData
)