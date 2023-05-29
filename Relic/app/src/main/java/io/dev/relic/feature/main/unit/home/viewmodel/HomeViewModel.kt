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
import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import io.dev.relic.core.data.network.api.dto.weather.WeatherApiDTO
import io.dev.relic.core.data.network.mappers.WeatherDataMapper.toWeatherInfoModel
import io.dev.relic.domain.location.ILocationTracker
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.repository.IWeatherDataRepository
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.LiveDataExt.observeOnce
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val locationTracker: ILocationTracker,
    private val databaseRepository: RelicDatabaseRepository,
    private val weatherDataRepository: IWeatherDataRepository
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
        accessDeviceLocation(
            doOnSuccess = {
                // Fetch the latest weather information data according to the current device location.
                fetchRemoteWeatherData(it)
            }
        )
    }

    /**
     * Try to access the current location of the device first
     * */
    private fun accessDeviceLocation(doOnSuccess: (location: Location) -> Unit) {
        LogUtil.verbose(TAG, "[LocationTracker] Attempts to get the current device location.")

        viewModelScope.launch {
            // Try to access the current location of the device first.
            locationTracker.getCurrentLocation()?.run {
                LogUtil.apply {
                    debug(TAG, "[LocationTracker] Get the location information succeeded.")
                    debug(TAG, "[LocationTracker] (latitude: ${latitude}, longitude:${longitude})")
                }

                doOnSuccess.invoke(this)
            } ?: run {
                LogUtil.error(TAG, "[LocationTracker] Couldn't retrieve the location of current device.")

                // Update the ui state to the Ui layer.
                _homeUiState = _homeUiState.copy(
                    isLoadingWeatherData = false,
                    errorMessageOfWeatherInfo = "Couldn't retrieve the location of current device."
                )
            }
        }
    }

    /* ======================== Remote ======================== */

    /**
     * Fetch the latest weather information data from the `remote server`
     * according to the `current device location`.
     *
     * @param location     The current location of the device.
     * */
    private fun fetchRemoteWeatherData(location: Location) {
        LogUtil.verbose(TAG, "[WeatherApi] Start requesting weather data.")

        viewModelScope.launch {
            // Update the ui state to the Ui layer.
            _homeUiState = _homeUiState.copy(
                isLoadingWeatherData = true,
                weatherInfoModel = null,
                errorMessageOfWeatherInfo = null
            )

            // Fetch the latest weather data from remote-server.
            val result: NetworkResult<WeatherApiDTO> = weatherDataRepository.getWeatherData(
                latitude = location.latitude,
                longitude = location.longitude
            )

            // Handle server result.
            when (result) {
                is NetworkResult.Failed -> {
                    LogUtil.apply {
                        error(TAG, "[WeatherApi] Failed to load weather data.")
                        error(TAG, "[WeatherApi] Error message: ${result.message}")
                    }

                    _homeUiState = _homeUiState.copy(
                        isLoadingWeatherData = false,
                        weatherInfoModel = null,
                        errorMessageOfWeatherInfo = result.message
                    )
                }

                is NetworkResult.Success -> {
                    LogUtil.apply {
                        debug(TAG, "[WeatherApi] Loading weather data succeeded.")
                        debug(TAG, "[WeatherApi] Datasource: ${result.data}")
                    }

                    result.data?.run {
                        // Update the ui state to the Ui layer.
                        _homeUiState = _homeUiState.copy(
                            isLoadingWeatherData = false,
                            weatherInfoModel = this.toWeatherInfoModel(),
                            errorMessageOfWeatherInfo = null
                        )
                    } ?: run {
                        // In fact, sometimes the request succeeds, but the server returns empty data.
                        LogUtil.debug(TAG, "[WeatherApi] Server error, retry it after.")

                        databaseRepository.readWeatherDataCache()
                            .asLiveData()
                            .observeOnce {
                                val offlineModel: WeatherInfoModel = it.first().weatherDatasource.toWeatherInfoModel()
                                // Update the ui state to the Ui layer.
                                _homeUiState = _homeUiState.copy(
                                    isLoadingWeatherData = false,
                                    weatherInfoModel = offlineModel,
                                    errorMessageOfWeatherInfo = "Server error, retry it after."
                                )
                            }
                    }
                }
            }
        }
    }

    private fun fetchRemoteFoodRecipesData() {
        // TODO
    }
}