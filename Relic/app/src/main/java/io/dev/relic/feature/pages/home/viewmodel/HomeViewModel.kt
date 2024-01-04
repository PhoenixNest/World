package io.dev.relic.feature.pages.home.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.core.database.repository.RelicDatabaseRepository
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.dto.weather.WeatherForecastDTO
import io.data.mappers.FoodRecipesDataMapper.toComplexSearchModelList
import io.data.mappers.WeatherDataMapper.toWeatherInfoModel
import io.data.model.NetworkResult
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.weather.WeatherDataState
import io.domain.use_case.food_receipes.FoodRecipesUseCase
import io.domain.use_case.todo.TodoUseCase
import io.domain.use_case.weather.WeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val databaseRepository: RelicDatabaseRepository,
    private val todoUseCase: TodoUseCase,
    private val weatherUseCase: WeatherUseCase,
    private val foodRecipesUseCase: FoodRecipesUseCase
) : AndroidViewModel(application) {

    /**
     * Indicate the current selected food recipes tab.
     * */
    private var _currentSelectedFoodRecipesTab: Int by mutableIntStateOf(0)

    /**
     * Check whether if already fetch the recipes data for initialize.
     * */
    private var _isFirstFetchFoodRecipes: Boolean = true

    /**
     * The number of results to skip (between 0 and 900).
     * */
    private var _foodRecipesOffset: Int = 0

    /**
     * The data flow of weather forecast.
     * */
    private val _weatherDataStateFlow: MutableStateFlow<WeatherDataState> = MutableStateFlow(WeatherDataState.Init)
    val weatherDataStateFlow: StateFlow<WeatherDataState> get() = _weatherDataStateFlow

    /**
     * The data flow of daily food recipes.
     * */
    private val _foodRecipesDataStateFlow: MutableStateFlow<FoodRecipesDataState> = MutableStateFlow(FoodRecipesDataState.Init)
    val foodRecipesDataStateFlow: StateFlow<FoodRecipesDataState> get() = _foodRecipesDataStateFlow

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        fetchFoodRecipesData(isRefresh = true)
    }

    /**
     * Fetch the latest weather info data from Remote-server.
     *
     * @param latitude
     * @param longitude
     * */
    fun fetchWeatherData(
        latitude: Double,
        longitude: Double
    ): StateFlow<NetworkResult<WeatherForecastDTO>> {
        return weatherUseCase
            .fetchWeatherData(
                latitude = latitude,
                longitude = longitude
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            )
            .also { stateFlow: StateFlow<NetworkResult<WeatherForecastDTO>> ->
                viewModelScope.launch {
                    stateFlow.collect { result: NetworkResult<WeatherForecastDTO> ->
                        handleRemoteWeatherData(result)
                    }
                }
            }
    }

    /**
     * [Search Recipes](https://spoonacular.com/food-api/docs#Search-Recipes-Complex)
     *
     * Search through thousands of recipes using advanced filtering and ranking.
     *
     * `NOTE`: This method combines searching by query, by ingredients, and by nutrients into one endpoint.
     *
     * @param isRefresh                  Whether is needed to refresh the data.
     * @param query                      The (natural language) recipe search query.
     * @param addRecipeInformation       If set to true, you get more information about the recipes returned.
     * @param addRecipeNutrition         If set to true, you get nutritional information about each recipes returned.
     *
     * @see FoodRecipesComplexSearchDTO
     * */
    fun fetchFoodRecipesData(
        isRefresh: Boolean,
        query: String = "coffee",
        addRecipeInformation: Boolean = true,
        addRecipeNutrition: Boolean = true,
    ): StateFlow<NetworkResult<FoodRecipesComplexSearchDTO>> {
        _isFirstFetchFoodRecipes = isRefresh
        _foodRecipesOffset += if (isRefresh) 0 else 10
        return foodRecipesUseCase
            .fetchComplexRecipesData(
                query = query,
                addRecipeInformation = addRecipeInformation,
                addRecipeNutrition = addRecipeNutrition,
                offset = _foodRecipesOffset
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            )
            .also { stateFlow: StateFlow<NetworkResult<FoodRecipesComplexSearchDTO>> ->
                viewModelScope.launch {
                    stateFlow.collect { result: NetworkResult<FoodRecipesComplexSearchDTO> ->
                        handleRemoteFoodRecipesData(result)
                    }
                }
            }
    }

    fun getSelectedFoodRecipesTab(): Int {
        return _currentSelectedFoodRecipesTab
    }

    fun updateSelectedFoodRecipesTab(newIndex: Int) {
        _currentSelectedFoodRecipesTab = newIndex
    }

    private fun handleRemoteWeatherData(result: NetworkResult<WeatherForecastDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Weather Data] Loading...")
                setState(_weatherDataStateFlow, WeatherDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also {
                    LogUtil.d(TAG, "[Handle Weather Data] Succeed, data: $it")
                    setState(_weatherDataStateFlow, WeatherDataState.FetchSucceed(it.toWeatherInfoModel()))
                } ?: {
                    LogUtil.w(TAG, "[Handle Weather Data] Succeed without data")
                    setState(_weatherDataStateFlow, WeatherDataState.NoWeatherData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode: Int? = result.code
                val errorMessage: String? = result.message
                LogUtil.e(TAG, "[Handle Weather Data] Failed, ($errorCode, $errorMessage)")
                setState(_weatherDataStateFlow, WeatherDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }

    private fun handleRemoteFoodRecipesData(result: NetworkResult<FoodRecipesComplexSearchDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Food Recipes Data] Loading...")
                setState(_foodRecipesDataStateFlow, FoodRecipesDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also {
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Succeed, data: $it")
                    setState(_foodRecipesDataStateFlow, FoodRecipesDataState.FetchSucceed(it.toComplexSearchModelList()))
                } ?: {
                    LogUtil.d(TAG, "[Handle Food Recipes Data] Succeed without data")
                    setState(_foodRecipesDataStateFlow, FoodRecipesDataState.NoFoodRecipesData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode: Int? = result.code
                val errorMessage: String? = result.message
                LogUtil.e(TAG, "[Handle Food Recipes Data] Failed, ($errorCode, $errorMessage)")
                setState(_foodRecipesDataStateFlow, FoodRecipesDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }
}