package io.dev.relic.domain.use_case.weather

import io.dev.relic.domain.use_case.weather.action.CacheWeatherData
import io.dev.relic.domain.use_case.weather.action.FetchRemoteWeatherData
import io.dev.relic.domain.use_case.weather.action.ReadCacheWeatherData

internal const val TAG = "WeatherUnitUseCase"

data class WeatherUseCase(
    val fetchRemoteWeatherData: FetchRemoteWeatherData,
    val cacheWeatherData: CacheWeatherData,
    val readCacheWeatherData: ReadCacheWeatherData
)