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
import io.dev.relic.core.data.network.monitor.FetchStatus
import io.dev.relic.core.data.network.monitor.FetchStatus.FETCH_SUCCEED
import io.dev.relic.core.data.network.monitor.IFetchDataMonitor
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.use_case.food_receipes.FoodRecipesUseCase
import io.dev.relic.domain.use_case.todo.TodoUseCase
import io.dev.relic.domain.use_case.weather.WeatherUseCase
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.ViewModelExt.setState
import kotlinx.coroutines.async
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

    private val _homeState: MutableStateFlow<HomeState> = MutableStateFlow(HomeState.Init)
    val homeState: StateFlow<HomeState> get() = _homeState

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

    fun exec(
        latitude: Double,
        longitude: Double,
        offset: Int
    ) {
        LogUtil.debug(TAG, "[Exec] Begin sequence...")
        viewModelScope.launch {
            val weatherFetchResult: Pair<FetchStatus, WeatherInfoModel?> = async {
                fetchWeatherForecastData(latitude = latitude, longitude = longitude)
            }.await()

            val foodRecipesFetchResult: Pair<FetchStatus, List<FoodRecipesComplexSearchInfoModel?>?> = async {
                fetchFoodRecipesData(offset = offset, fetchMore = false)
            }.await()

            if (weatherFetchResult.first == FETCH_SUCCEED && foodRecipesFetchResult.first == FETCH_SUCCEED) {
                setState(_homeState, HomeState.FetchDataSucceed(weatherData = null, recipesData = null))
            }
        }
    }

    fun fetchMoreFoodRecipesData(newOffset: Int) {
        viewModelScope.launch {
            fetchFoodRecipesData(newOffset, true)
        }
    }

    private suspend fun fetchWeatherForecastData(
        latitude: Double,
        longitude: Double
    ): Pair<FetchStatus, WeatherInfoModel?> {
        return weatherUseCase.fetchWeatherData.invoke(
            latitude = latitude,
            longitude = longitude,
            listener = object : IFetchDataMonitor {
                override fun onFetching() {
                    setState(_homeState, HomeState.FetchingData)
                }

                override fun <T> onFetchSucceed(dto: T) {
                    val dataModel: WeatherInfoModel = (dto as WeatherForecastDTO).toWeatherInfoModel()
                    setState(_homeState, HomeState.FetchWeatherDataSucceed(dataModel))
                }

                override fun onFetchSucceedButNoData(errorMessage: String) {
                    setState(_homeState, HomeState.NoWeatherData)
                }

                override fun onFetchFailed(errorCode: Int?, errorMessage: String?) {
                    setState(_homeState, HomeState.FetchWeatherDataFailed(errorCode, errorMessage))
                }
            }
        )
    }

    private suspend fun fetchFoodRecipesData(
        offset: Int,
        fetchMore: Boolean
    ): Pair<FetchStatus, List<FoodRecipesComplexSearchInfoModel?>?> {
        return foodRecipesUseCase.fetchComplexRecipesData.invoke(
            offset = offset,
            listener = object : IFetchDataMonitor {
                override fun onFetching() {
                    setState(_homeState, HomeState.FetchingData)
                }

                override fun <T> onFetchSucceed(dto: T) {
                    val dataList: List<FoodRecipesComplexSearchInfoModel> = (dto as FoodRecipesComplexSearchDTO).toComplexSearchModelList()
                    setState(_homeState, HomeState.FetchFoodRecipesDataSucceed(dataList))
                }

                override fun onFetchSucceedButNoData(errorMessage: String) {
                    setState(_homeState, HomeState.NoFoodRecipesData)
                }

                override fun onFetchFailed(errorCode: Int?, errorMessage: String?) {
                    setState(_homeState, HomeState.FetchFoodRecipesDataFailed(errorCode, errorMessage))
                }
            }
        )
    }
}