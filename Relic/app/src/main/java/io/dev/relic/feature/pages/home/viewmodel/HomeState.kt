package io.dev.relic.feature.pages.home.viewmodel

import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.domain.model.weather.WeatherInfoModel

sealed class HomeState {

    /* Common */

    data object Init : HomeState()

    data object Empty : HomeState()

    data object NoWeatherData : HomeState()

    data object NoFoodRecipesData : HomeState()

    /* Loading */

    data object FetchingData : HomeState()

    /* Succeed */

    data class FetchDataSucceed(
        val model: WeatherInfoModel?,
        val modelList: List<FoodRecipesComplexSearchInfoModel>?
    ) : HomeState()

    data class FetchWeatherDataSucceed(
        val model: WeatherInfoModel?
    ) : HomeState()

    data class FetchFoodRecipesDataSucceed(
        val modelList: List<FoodRecipesComplexSearchInfoModel>?
    ) : HomeState()

    /* Failed */

    data class FetchDataFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeState()

    data class FetchWeatherDataFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeState()

    data class FetchFoodRecipesDataFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeState()

}
