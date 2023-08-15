package io.dev.relic.feature.screen.main.sub_page.home.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
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
import io.dev.relic.domain.use_case.weather.WeatherUseCase
import io.dev.relic.feature.GlobalViewModel
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.LiveDataExt.observeOnce
import io.dev.relic.global.utils.ext.ViewModelExt.setState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    application: Application,
    private val weatherUseCase: WeatherUseCase,
    private val foodRecipesUseCase: FoodRecipesUseCase
) : GlobalViewModel(application) {

    var offlineWeatherData: WeatherInfoModel? = null
    var offlineFoodRecipesListData: MutableList<FoodRecipesComplexSearchInfoModel> = mutableListOf()

    private val _homeStateFlow: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Init)
    val homeStateFlow: SharedFlow<HomeUiState> = _homeStateFlow

    companion object {
        private const val TAG = "HomeViewModel"
    }

    init {
        readOfflineDataFromDatabase()
    }

    /* ======================== Logical ======================== */

    fun exec(location: Location) {
        fetchWeatherData(location)
        fetchFoodRecipesData(0)
    }

    /**
     * Read the cache data from database.
     *
     * @see readCacheWeatherData
     * @see readCacheComplexRecipesData
     *
     * @see fetchWeatherData
     * @see fetchFoodRecipesData
     * */
    private fun readOfflineDataFromDatabase() {
        readCacheWeatherData()
        readCacheComplexRecipesData()
    }

    private fun readCacheWeatherData() {
        weatherUseCase.readCacheWeatherData.invoke()
            .asLiveData()
            .observeOnce { weatherEntities: List<WeatherEntity> ->
                offlineWeatherData = weatherEntities
                    .first()
                    .weatherDatasource
                    .toWeatherInfoModel()
            }
    }

    private fun readCacheComplexRecipesData() {
        foodRecipesUseCase.readCacheComplexRecipesData.invoke()
            .asLiveData()
            .observeOnce { entityList: List<FoodRecipesComplexSearchEntity> ->
                offlineFoodRecipesListData.clear()
                offlineFoodRecipesListData.addAll(
                    elements = entityList
                        .first()
                        .foodRecipesComplexSearchDTO
                        .toComplexSearchModelList()
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
    private fun fetchWeatherData(location: Location) {
        viewModelScope.launch {
            weatherUseCase.fetchWeatherData.invoke(
                latitude = location.latitude,
                longitude = location.longitude,
                listener = object : IFetchDataMonitor {
                    override fun onFetching() {
                        LogUtil.debug(TAG, "[Fetch Weather Data] Fetching...")
                        setState(
                            stateFlow = _homeStateFlow,
                            newState = HomeUiState.FetchingWeatherData
                        )
                    }

                    override fun <T> onFetchSucceed(dto: T) {
                        val dataDTO: WeatherForecastDTO = (dto as WeatherForecastDTO)
                        LogUtil.debug(TAG, "[Fetch Weather Data] Fetch succeed, $dto")
                        setState(
                            stateFlow = _homeStateFlow,
                            newState = HomeUiState.FetchWeatherDataSucceed(dataDTO.toWeatherInfoModel())
                        )
                    }

                    override fun onFetchSucceedButNoData(errorMessage: String) {
                        LogUtil.warning(TAG, "[Fetch Weather Data] Fetch succeed but not data, load the cache data from database.")
                        setState(
                            stateFlow = _homeStateFlow,
                            newState = HomeUiState.FetchWeatherDataFailed(
                                errorCode = null,
                                errorMessage = errorMessage,
                                offlineData = offlineWeatherData
                            )
                        )
                    }

                    override fun onFetchFailed(errorMessage: String?) {
                        LogUtil.error(TAG, "[Fetch Weather Data] Fetch failed, errorMessage: $errorMessage")
                        setState(
                            _homeStateFlow,
                            HomeUiState.FetchWeatherDataFailed(
                                errorCode = null,
                                errorMessage = errorMessage,
                                offlineData = offlineWeatherData
                            )
                        )
                    }
                }
            )
        }
    }

    /**
     * Fetch the latest data of food recipes data by using complex search.
     *
     * @param offset    The current page index
     * */
    private fun fetchFoodRecipesData(
        offset: Int
    ) {
        viewModelScope.launch {
            foodRecipesUseCase.fetchComplexRecipesData.invoke(
                offset = offset,
                listener = object : IFetchDataMonitor {
                    override fun onFetching() {
                        setState(
                            stateFlow = _homeStateFlow,
                            newState = HomeUiState.FetchingFoodRecipesData
                        )
                    }

                    override fun <T> onFetchSucceed(dto: T) {
                        val dataDTO: FoodRecipesComplexSearchDTO = (dto as FoodRecipesComplexSearchDTO)
                        setState(
                            stateFlow = _homeStateFlow,
                            newState = HomeUiState.FetchFoodRecipesDataSucceed(dataDTO.toComplexSearchModelList())
                        )
                    }

                    override fun onFetchSucceedButNoData(errorMessage: String) {
                        setState(
                            stateFlow = _homeStateFlow,
                            newState = HomeUiState.FetchFoodRecipesDataFailed(
                                errorCode = null,
                                errorMessage = errorMessage,
                                offlineData = offlineFoodRecipesListData
                            )
                        )
                    }

                    override fun onFetchFailed(errorMessage: String?) {
                        setState(
                            stateFlow = _homeStateFlow,
                            newState = HomeUiState.FetchFoodRecipesDataFailed(
                                errorCode = null,
                                errorMessage = errorMessage,
                                offlineData = offlineFoodRecipesListData
                            )
                        )
                    }
                }
            )
        }
    }
}