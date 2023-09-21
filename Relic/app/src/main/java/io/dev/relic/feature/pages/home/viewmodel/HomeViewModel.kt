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
import io.dev.relic.global.utils.ext.ViewModelExt.setState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val databaseRepository: RelicDatabaseRepository,
    private val todoUseCase: TodoUseCase,
    private val weatherUseCase: WeatherUseCase,
    private val foodRecipesUseCase: FoodRecipesUseCase
) : AndroidViewModel(application) {

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

    fun execRemoteWeatherDataFlow(
        latitude: Double,
        longitude: Double
    ): StateFlow<NetworkResult<WeatherForecastDTO>> {
        return weatherUseCase
            .fetchWeatherData(latitude, longitude)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            )
    }

    fun execRemoteFoodRecipesDataFlow(
        offset: Int
    ): StateFlow<NetworkResult<FoodRecipesComplexSearchDTO>> {
        return foodRecipesUseCase
            .fetchComplexRecipesData(offset)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            )
    }

    private fun handleRemoteWeatherData(result: NetworkResult<WeatherForecastDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                setState(_weatherDataStateFlow, HomeWeatherDataState.Fetching)
            }

            is NetworkResult.Success -> {
                setState(_weatherDataStateFlow, HomeWeatherDataState.FetchSucceed(result.data?.toWeatherInfoModel()))
            }

            is NetworkResult.Failed -> {
                setState(_weatherDataStateFlow, HomeWeatherDataState.FetchFailed(result.code, result.message))
            }
        }
    }

    private fun handleRemoteFoodRecipesData(result: NetworkResult<FoodRecipesComplexSearchDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                setState(_foodRecipesDataStateFlow, HomeFoodRecipesDataState.Fetching)
            }

            is NetworkResult.Success -> {
                setState(_foodRecipesDataStateFlow, HomeFoodRecipesDataState.FetchSucceed(result.data?.toComplexSearchModelList()))
            }

            is NetworkResult.Failed -> {
                setState(_foodRecipesDataStateFlow, HomeFoodRecipesDataState.FetchFailed(result.code, result.message))
            }
        }
    }
}