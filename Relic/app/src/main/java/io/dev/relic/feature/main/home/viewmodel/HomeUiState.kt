package io.dev.relic.feature.main.home.viewmodel

import io.dev.relic.domain.model.weather.WeatherData

data class HomeUiState(
    val isLoading: Boolean = false,
    val weatherData: WeatherData? = null,
    val error: String? = null
)