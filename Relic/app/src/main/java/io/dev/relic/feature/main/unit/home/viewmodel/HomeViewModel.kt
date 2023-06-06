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
import io.dev.relic.core.data.database.entity.WeatherEntity
import io.dev.relic.core.data.network.api.dto.weather.WeatherForecastDTO
import io.dev.relic.core.data.network.mappers.WeatherDataMapper.toWeatherInfoModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.use_case.lcoation.LocationUseCase
import io.dev.relic.domain.use_case.lcoation.action.AccessCurrentLocation
import io.dev.relic.domain.use_case.weather.WeatherUseCase
import io.dev.relic.domain.use_case.weather.action.FetchRemoteWeatherData
import io.dev.relic.global.utils.ext.LiveDataExt.observeOnce
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val locationUseCase: LocationUseCase,
    private val weatherUseCase: WeatherUseCase
) : AndroidViewModel(application) {

    private var _homeUiState: HomeUiState by mutableStateOf(value = HomeUiState())
    val homeUiState: HomeUiState get() = _homeUiState

    private var _homeUiStateFlow: MutableSharedFlow<HomeUiState> = MutableSharedFlow()
    val homeUiStateFlow: SharedFlow<HomeUiState> get() = _homeUiStateFlow

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
                        _homeUiState = _homeUiState.copy(isAccessDeviceLocation = true)
                    }

                    override fun onAccessSucceed(location: Location) {
                        // Update the ui state to the Ui layer.
                        _homeUiState = _homeUiState.copy(isAccessDeviceLocation = false)

                        // Fetch the latest weather information data according to the current device location.
                        fetchRemoteWeatherData(location)
                    }

                    override fun onAccessFailed(errorMessage: String) {
                        // Update the ui state to the Ui layer.
                        _homeUiState = _homeUiState.copy(
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
                listener = object : FetchRemoteWeatherData.IWeatherListener {
                    override fun onFetching() {
                        // Update the ui state to the Ui layer.
                        _homeUiState = _homeUiState.copy(
                            isLoadingWeatherData = true,
                            weatherInfoModel = null,
                            errorMessageOfWeatherInfo = null
                        )
                    }

                    override fun onFetchSucceed(weatherForecastDTO: WeatherForecastDTO) {
                        // Update the ui state to the Ui layer.
                        _homeUiState = _homeUiState.copy(
                            isLoadingWeatherData = false,
                            weatherInfoModel = weatherForecastDTO.toWeatherInfoModel(),
                            errorMessageOfWeatherInfo = null
                        )
                    }

                    override fun onFetchSucceedButNoData(errorMessage: String) {
                        weatherUseCase.readCacheWeatherData.invoke()
                            .asLiveData()
                            .observeOnce { weatherEntities: List<WeatherEntity> ->
                                // Read the cache data from database.
                                val offlineModel: WeatherInfoModel = weatherEntities
                                    .first()
                                    .weatherDatasource
                                    .toWeatherInfoModel()

                                // Update the ui state to the Ui layer.
                                _homeUiState = _homeUiState.copy(
                                    isLoadingWeatherData = false,
                                    weatherInfoModel = offlineModel,
                                    errorMessageOfWeatherInfo = errorMessage
                                )
                            }
                    }

                    override fun onFetchFailed(errorMessage: String?) {
                        // Update the ui state to the Ui layer.
                        _homeUiState = _homeUiState.copy(
                            isLoadingWeatherData = false,
                            weatherInfoModel = null,
                            errorMessageOfWeatherInfo = errorMessage
                        )
                    }

                }
            )
        }
    }

    private fun fetchRemoteFoodRecipesData() {
        // TODO
    }
}