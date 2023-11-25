package io.domain.use_case.weather

import io.domain.use_case.weather.action.CacheWeatherData
import io.domain.use_case.weather.action.FetchWeatherData
import io.domain.use_case.weather.action.ReadCacheWeatherData

internal const val TAG = "WeatherUnitUseCase"

data class WeatherUseCase(
    val fetchWeatherData: FetchWeatherData,
    val cacheWeatherData: CacheWeatherData,
    val readCacheWeatherData: ReadCacheWeatherData
)