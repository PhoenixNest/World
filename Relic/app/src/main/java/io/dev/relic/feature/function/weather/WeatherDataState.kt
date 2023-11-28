package io.dev.relic.feature.function.weather

import io.data.model.weather.WeatherInfoModel

sealed class WeatherDataState {

    /* Common */

    data object Init : WeatherDataState()

    data object Empty : WeatherDataState()

    data object NoWeatherData : WeatherDataState()

    /* Loading */

    data object Fetching : WeatherDataState()

    /* Succeed */

    data class FetchSucceed(
        val model: WeatherInfoModel?
    ) : WeatherDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : WeatherDataState()

}