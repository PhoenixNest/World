package io.dev.relic.feature.screen.main.sub_page.home.viewmodel

import android.location.Location
import androidx.compose.runtime.Stable
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.domain.model.weather.WeatherInfoModel

@Stable
sealed class HomeUiState {

    /* Common */

    object Init : HomeUiState()

    object Empty : HomeUiState()

    /* Loading */

    object AccessingLocation : HomeUiState()

    object FetchingWeatherData : HomeUiState()

    object FetchingFoodRecipesData : HomeUiState()

    /* Succeed */

    data class ReadTodoDataSucceed(
        val data: List<TodoEntity>
    ) : HomeUiState()

    data class AccessLocationSucceed(
        val location: Location?
    ) : HomeUiState()

    data class FetchWeatherDataSucceed(
        val data: WeatherInfoModel?
    ) : HomeUiState()

    data class FetchFoodRecipesDataSucceed(
        val data: List<FoodRecipesComplexSearchInfoModel>?
    ) : HomeUiState()

    /* Succeed but no data */

    data class ReadTodoDataSucceedButNoData(
        val data: List<TodoEntity>
    ) : HomeUiState()

    data class AccessLocationSucceedButNoData(
        val location: Location?
    ) : HomeUiState()

    data class FetchWeatherDataSucceedButNoData(
        val data: WeatherInfoModel?
    ) : HomeUiState()

    data class FetchFoodRecipesDataSucceedButNoData(
        val data: List<FoodRecipesComplexSearchInfoModel>?
    ) : HomeUiState()

    /* Failed */

    data class ReadTodoDataFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeUiState()

    data class AccessLocationFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeUiState()

    data class FetchWeatherDataFailed(
        val errorCode: Int?,
        val errorMessage: String?,
        val offlineData: WeatherInfoModel?
    ) : HomeUiState()

    data class FetchFoodRecipesDataFailed(
        val errorCode: Int?,
        val errorMessage: String?,
        val offlineData: List<FoodRecipesComplexSearchInfoModel>?
    ) : HomeUiState()

}