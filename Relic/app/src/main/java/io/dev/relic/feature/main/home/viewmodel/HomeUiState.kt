package io.dev.relic.feature.main.home.viewmodel

import io.dev.relic.domin.model.weather.WeatherData

data class HomeUiState(
    val isLoading: Boolean = false,
    val weatherData: WeatherData? = null,
    val error: String? = null
)