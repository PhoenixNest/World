package io.dev.relic.feature.main.unit.home.viewmodel

import io.dev.relic.domain.model.weather.WeatherInfoModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val weatherInfo: WeatherInfoModel? = null,
    val error: String? = null
)