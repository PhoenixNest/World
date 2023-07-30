package io.dev.relic.feature.screen.main.sub_page.home.viewmodel

import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.domain.model.weather.WeatherInfoModel

data class HomeUiState(

    /* ======================== Loading ======================== */

    val isLoadingTodoData: Boolean = false,
    val isAccessDeviceLocation: Boolean = false,
    val isLoadingWeatherData: Boolean = false,
    val isLoadingFoodRecipesData: Boolean = false,

    /* ======================== Data ======================== */

    val todoDataList: List<TodoEntity>? = null,
    val weatherData: WeatherInfoModel? = null,
    val foodRecipesDataList: List<FoodRecipesComplexSearchInfoModel>? = null,

    /* ======================== Error message ======================== */

    val errorMessageOfTodoInfo: String? = null,
    val errorMessageOfDeviceLocation: String? = null,
    val errorMessageOfWeatherInfo: String? = null,
    val errorMessageOfFoodRecipes: String? = null

)