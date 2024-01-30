package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.lazy.LazyListScope
import io.dev.relic.feature.function.weather.WeatherDataState
import io.dev.relic.feature.function.weather.ui.WeatherCard

@Suppress("FunctionName")
fun LazyListScope.HomeWeatherPanel(
    weatherDataState: WeatherDataState,
    onWeatherRetry: () -> Unit
) {
    item {
        WeatherCard(
            weatherDataState = weatherDataState,
            onRetryClick = onWeatherRetry
        )
    }
}