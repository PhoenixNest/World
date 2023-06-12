package io.dev.relic.feature.main.unit.home.viewmodel

import android.app.Application
import android.location.Location
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.data.database.entity.FoodRecipesComplexSearchEntity
import io.dev.relic.core.data.database.entity.WeatherEntity
import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.core.data.network.mappers.FoodRecipesDataMapper.toComplexSearchModelList
import io.dev.relic.core.data.network.mappers.WeatherDataMapper.toWeatherInfoModel
import io.dev.relic.core.data.network.monitor.IFetchDataMonitor
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.use_case.food_receipes.FoodRecipesUseCase
import io.dev.relic.domain.use_case.lcoation.LocationUseCase
import io.dev.relic.domain.use_case.lcoation.action.AccessCurrentLocation
import io.dev.relic.domain.use_case.weather.WeatherUseCase
import io.dev.relic.global.utils.ext.LiveDataExt.observeOnce
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val locationUseCase: LocationUseCase,
    private val weatherUseCase: WeatherUseCase,
    private val foodRecipesUseCase: FoodRecipesUseCase
) : AndroidViewModel(application) {

    var state: HomeUiState by mutableStateOf(value = HomeUiState())
        private set

    companion object {
        private const val TAG = "HomeViewModel"
    }

    /* ======================== Logical ======================== */

    init {
        execute()
    }

    private fun execute() {
        accessDeviceLocation()
    }

    /**
     * Try to access the current location of the device first
     *
     * @see fetchRemoteWeatherData
     * */
    private fun accessDeviceLocation() {
        viewModelScope.launch {
            locationUseCase.accessCurrentLocation.invoke(
                listener = object : AccessCurrentLocation.ILocationListener {
                    override fun onAccessing() {
                        // Update the ui state to the Ui layer.
                        state = state.copy(isAccessDeviceLocation = true)
                    }

                    override fun onAccessSucceed(location: Location) {
                        // Update the ui state to the Ui layer.
                        state = state.copy(isAccessDeviceLocation = false)

                        // Fetch the latest weather information data according to the current device location.
                        fetchRemoteWeatherData(location)
                    }

                    override fun onAccessFailed(errorMessage: String) {
                        // Update the ui state to the Ui layer.
                        state = state.copy(
                            isLoadingWeatherData = false,
                            errorMessageOfDeviceLocation = errorMessage
                        )
                    }

                }
            )
        }
    }

    /* ======================== Remote ======================== */

    /**
     * Fetch the latest weather information data from the `remote server`
     * according to the `current device location`.
     *
     * @param location     The current location of the device.
     * @see accessDeviceLocation
     * */
    private fun fetchRemoteWeatherData(location: Location) {
        viewModelScope.launch {
            weatherUseCase.fetchRemoteWeatherData.invoke(
                latitude = location.latitude,
                longitude = location.longitude,
                listener = object : IFetchDataMonitor {
                    override fun onFetching() {
                        state = state.copy(
                            isLoadingWeatherData = true,
                            weatherInfoModel = null,
                            errorMessageOfWeatherInfo = null
                        )
                    }

                    override fun <T> onFetchSucceed(dto: T) {
                        state = state.copy(
                            isLoadingWeatherData = false,
                            weatherInfoModel = (dto as WeatherForecastDTO).toWeatherInfoModel(),
                            errorMessageOfWeatherInfo = null
                        )
                    }

                    override fun onFetchSucceedButNoData(errorMessage: String) {
                        // Read the cache data from database.
                        weatherUseCase.readCacheWeatherData.invoke()
                            .asLiveData()
                            .observeOnce { weatherEntities: List<WeatherEntity> ->
                                val offlineModel: WeatherInfoModel = weatherEntities
                                    .first()
                                    .weatherDatasource
                                    .toWeatherInfoModel()

                                state = state.copy(
                                    isLoadingWeatherData = false,
                                    weatherInfoModel = offlineModel,
                                    errorMessageOfWeatherInfo = errorMessage
                                )
                            }
                    }

                    override fun onFetchFailed(errorMessage: String?) {
                        state = state.copy(
                            isLoadingWeatherData = false,
                            weatherInfoModel = null,
                            errorMessageOfWeatherInfo = errorMessage
                        )
                    }

                }
            )
        }
    }

    /**
     * Fetch the latest data
     * */
    private fun fetchRemoteFoodRecipesData(
        offset: Int
    ) {
        viewModelScope.launch {
            foodRecipesUseCase.fetchRemoteComplexRecipesData.invoke(
                offset = offset,
                listener = object : IFetchDataMonitor {
                    override fun onFetching() {
                        state = state.copy(
                            isLoadingFoodRecipesData = true,
                            foodRecipesInfoModelList = null,
                            errorMessageOfDeviceLocation = null
                        )
                    }

                    override fun <T> onFetchSucceed(dto: T) {
                        state = state.copy(
                            isLoadingTodoData = false,
                            foodRecipesInfoModelList = (dto as FoodRecipesComplexSearchDTO).toComplexSearchModelList(),
                            errorMessageOfFoodRecipes = null
                        )
                    }

                    override fun onFetchSucceedButNoData(errorMessage: String) {
                        // TODO: Load the cache which be stored in the database.
                        foodRecipesUseCase.readCacheComplexRecipesData.invoke()
                            .asLiveData()
                            .observeOnce { entityList: List<FoodRecipesComplexSearchEntity> ->
                                val offlineModelList: List<FoodRecipesComplexSearchInfoModel> = entityList
                                    .first()
                                    .foodRecipesComplexSearchDTO
                                    .toComplexSearchModelList()

                                state = state.copy(
                                    isLoadingTodoData = false,
                                    foodRecipesInfoModelList = offlineModelList,
                                    errorMessageOfFoodRecipes = errorMessage
                                )
                            }

                    }

                    override fun onFetchFailed(errorMessage: String?) {
                        state = state.copy(
                            isLoadingTodoData = false,
                            foodRecipesInfoModelList = null,
                            errorMessageOfFoodRecipes = errorMessage
                        )
                    }

                }
            )
        }
    }
}