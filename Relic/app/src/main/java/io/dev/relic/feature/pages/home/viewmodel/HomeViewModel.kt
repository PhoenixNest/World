package io.dev.relic.feature.pages.home.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.dto.weather.WeatherForecastDTO
import io.data.entity.FoodRecipesComplexSearchEntity
import io.data.entity.TodoEntity
import io.data.entity.WeatherEntity
import io.data.mappers.FoodRecipesDataMapper.toComplexSearchModelList
import io.data.mappers.WeatherDataMapper.toWeatherInfoModel
import io.data.model.NetworkResult
import io.dev.relic.domain.use_case.food_receipes.FoodRecipesUseCase
import io.dev.relic.domain.use_case.todo.TodoUseCase
import io.dev.relic.domain.use_case.weather.WeatherUseCase
import io.dev.relic.feature.pages.home.viewmodel.state.HomeFoodRecipesDataState
import io.dev.relic.feature.pages.home.viewmodel.state.HomeWeatherDataState
import io.module.common.ext.ViewModelExt.setState
import io.module.common.util.LogUtil
import io.module.core.database.repository.RelicDatabaseRepository
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
    var currentSelectedFoodRecipesTab: Int by mutableIntStateOf(0)

    /**
     * Check whether if already fetch the recipes data for initialize.
     * */
    private var isFirstFetchFoodRecipes: Boolean = true

    /**
     * The number of results to skip (between 0 and 900).
     * */
    private var foodRecipesOffset: Int = 0

    /**
     * The data flow of weather forecast.
     * */
    private val _weatherDataStateFlow: MutableStateFlow<HomeWeatherDataState> = MutableStateFlow(HomeWeatherDataState.Init)
    val weatherDataStateFlow: StateFlow<HomeWeatherDataState> get() = _weatherDataStateFlow

    /**
     * The data flow of daily food recipes.
     * */
    private val _foodRecipesDataStateFlow: MutableStateFlow<HomeFoodRecipesDataState> = MutableStateFlow(HomeFoodRecipesDataState.Init)
    val foodRecipesDataStateFlow: StateFlow<HomeFoodRecipesDataState> get() = _foodRecipesDataStateFlow

    private val localWeatherData: StateFlow<List<WeatherEntity>> = databaseRepository
        .readWeatherDataCache()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5 * 1000L),
            initialValue = emptyList()
        )

    private val localTodoData: StateFlow<List<TodoEntity>> = databaseRepository
        .readAllTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5 * 1000L),
            initialValue = emptyList()
        )

    private val localFoodRecipesData: StateFlow<List<FoodRecipesComplexSearchEntity>> = databaseRepository
        .readComplexSearchRecipesCache()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5 * 1000L),
            initialValue = emptyList()
        )

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        fetchFoodRecipesData(isRefresh = true)
    }

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
        isFirstFetchFoodRecipes = isRefresh
        foodRecipesOffset += if (isRefresh) 0 else 10
        return foodRecipesUseCase
            .fetchComplexRecipesData(
                query = query,
                addRecipeInformation = addRecipeInformation,
                addRecipeNutrition = addRecipeNutrition,
                offset = foodRecipesOffset
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

    fun updateSelectedFoodRecipesTabs(newIndex: Int) {
        currentSelectedFoodRecipesTab = newIndex
    }

    private fun handleRemoteWeatherData(result: NetworkResult<WeatherForecastDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.debug(TAG, "[Handle Weather Data] Loading...")
                setState(_weatherDataStateFlow, HomeWeatherDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also {
                    LogUtil.debug(TAG, "[Handle Weather Data] Succeed, data: $it")
                    setState(_weatherDataStateFlow, HomeWeatherDataState.FetchSucceed(it.toWeatherInfoModel()))
                } ?: {
                    LogUtil.warning(TAG, "[Handle Weather Data] Succeed without data")
                    setState(_weatherDataStateFlow, HomeWeatherDataState.NoWeatherData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode: Int? = result.code
                val errorMessage: String? = result.message
                LogUtil.error(TAG, "[Handle Weather Data] Failed, ($errorCode, $errorMessage)")
                setState(_weatherDataStateFlow, HomeWeatherDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }

    private fun handleRemoteFoodRecipesData(result: NetworkResult<FoodRecipesComplexSearchDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.debug(TAG, "[Handle Food Recipes Data] Loading...")
                setState(_foodRecipesDataStateFlow, HomeFoodRecipesDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also {
                    LogUtil.debug(TAG, "[Handle Food Recipes Data] Succeed, data: $it")
                    setState(_foodRecipesDataStateFlow, HomeFoodRecipesDataState.FetchSucceed(it.toComplexSearchModelList()))
                } ?: {
                    LogUtil.debug(TAG, "[Handle Food Recipes Data] Succeed without data")
                    setState(_foodRecipesDataStateFlow, HomeFoodRecipesDataState.NoFoodRecipesData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode: Int? = result.code
                val errorMessage: String? = result.message
                LogUtil.error(TAG, "[Handle Food Recipes Data] Failed, ($errorCode, $errorMessage)")
                setState(_foodRecipesDataStateFlow, HomeFoodRecipesDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }
}