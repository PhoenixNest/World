package io.dev.relic.feature.pages.home.viewmodel

import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.domain.model.weather.WeatherInfoModel

sealed class HomeUiState {

    /* Common */

    data object Init : HomeUiState()

    data object Empty : HomeUiState()

    data object NoWeatherData : HomeUiState()

    data object NoFoodRecipesData : HomeUiState()

    /* Loading */

    data object FetchingData : HomeUiState()

    /* Succeed */

    data class FetchWeatherDataSucceed(
        val weatherInfoModel: WeatherInfoModel?
    ) : HomeUiState()

    data class FetchFoodRecipesDataSucceed(
        val foodRecipesModel: List<FoodRecipesComplexSearchInfoModel>?
    ) : HomeUiState()

    /* Failed */

    data class FetchWeatherDataFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeUiState()

    data class FetchFoodRecipesDataFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeUiState()

}
