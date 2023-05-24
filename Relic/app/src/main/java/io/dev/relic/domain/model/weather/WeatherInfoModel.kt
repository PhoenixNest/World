package io.dev.relic.domain.model.weather

data class WeatherInfoModel(
    val weatherDataPerDay: Map<Int, List<WeatherDataModel>>,
    val currentWeatherData: WeatherDataModel?
)