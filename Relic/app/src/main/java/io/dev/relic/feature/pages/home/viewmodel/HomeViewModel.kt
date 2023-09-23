package io.dev.relic.feature.pages.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.data.database.entity.FoodRecipesComplexSearchEntity
import io.dev.relic.core.data.database.entity.TodoEntity
import io.dev.relic.core.data.database.entity.WeatherEntity
import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.core.data.network.mappers.FoodRecipesDataMapper.toComplexSearchModelList
import io.dev.relic.core.data.network.mappers.WeatherDataMapper.toWeatherInfoModel
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.use_case.food_receipes.FoodRecipesUseCase
import io.dev.relic.domain.use_case.todo.TodoUseCase
import io.dev.relic.domain.use_case.weather.WeatherUseCase
import io.dev.relic.feature.pages.home.viewmodel.state.HomeFoodRecipesDataState
import io.dev.relic.feature.pages.home.viewmodel.state.HomeWeatherDataState
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.ViewModelExt.setState
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

    private var isFirstFetchFoodRecipes: Boolean = true

    /**
     * The number of results to skip (between 0 and 900).
     * */
    private var foodRecipesOffset: Int = 0

    private val _weatherDataStateFlow: MutableStateFlow<HomeWeatherDataState> = MutableStateFlow(HomeWeatherDataState.Init)
    val weatherDataStateFlow: StateFlow<HomeWeatherDataState> get() = _weatherDataStateFlow

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
        fetchFoodRecipesData(
            isRefresh = false,
            query = "coffee",
            addRecipeInformation = true,
            addRecipeNutrition = true,
        )
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
        query: String,
        addRecipeInformation: Boolean,
        addRecipeNutrition: Boolean,
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