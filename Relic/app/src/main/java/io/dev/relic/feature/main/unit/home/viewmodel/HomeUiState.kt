package io.dev.relic.feature.main.unit.home.viewmodel

import io.dev.relic.domain.model.todo.TodoDataModel
import io.dev.relic.domain.model.weather.WeatherInfoModel

data class HomeUiState(

    /**
     * Check the current access status of the device's location.
     * */
    val isAccessDeviceLocation: Boolean = false,

    /**
     * Check the current loading of weather data.
     * */
    val isLoadingWeatherData: Boolean = false,

    /**
     * Check the current loading of todo data.
     * */
    val isLoadingTodoData: Boolean = false,

    /**
     * Check the current loading of food recipes data.
     * */
    val isLoadingFoodRecipesData: Boolean = false,

    /**
     * Weather data model.
     * */
    val weatherInfoModel: WeatherInfoModel? = null,

    /**
     * Todo data model.
     * */
    val todoDataList: List<TodoDataModel>? = null,

    /**
     * Error message displayed when accessing fail of the current device's location.
     * */
    val errorMessageOfDeviceLocation: String? = null,

    /**
     * Error message displayed when the weather data failed to load.
     * */
    val errorMessageOfWeatherInfo: String? = null,

    /**
     * Error message displayed when the todo data failed to load.
     * */
    val errorMessageOfTodoInfo: String? = null
)