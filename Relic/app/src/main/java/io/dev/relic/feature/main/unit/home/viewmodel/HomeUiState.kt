package io.dev.relic.feature.main.unit.home.viewmodel

import io.dev.relic.domain.model.weather.WeatherDataModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val weatherDataModel: WeatherDataModel? = null,
    val error: String? = null
)