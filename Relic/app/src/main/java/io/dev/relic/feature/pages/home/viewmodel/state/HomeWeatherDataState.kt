package io.dev.relic.feature.pages.home.viewmodel.state

import io.dev.relic.domain.model.weather.WeatherInfoModel

sealed class HomeWeatherDataState {

    /* Common */

    data object Init : HomeWeatherDataState()

    data object Empty : HomeWeatherDataState()

    data object NoWeatherData : HomeWeatherDataState()

    /* Loading */

    data object Fetching : HomeWeatherDataState()

    /* Succeed */

    data class FetchSucceed(
        val model: WeatherInfoModel?
    ) : HomeWeatherDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeWeatherDataState()

}