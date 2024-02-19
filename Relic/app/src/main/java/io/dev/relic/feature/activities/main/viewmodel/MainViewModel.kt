package io.dev.relic.feature.activities.main.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.data.dto.weather.WeatherForecastDTO
import io.data.mappers.WeatherDataMapper.toWeatherEntity
import io.data.mappers.WeatherDataMapper.toWeatherInfoModel
import io.data.model.NetworkResult
import io.dev.relic.feature.function.weather.WeatherDataState
import io.dev.relic.feature.screens.main.MainState
import io.domain.use_case.lcoation.LocationUseCase
import io.domain.use_case.weather.WeatherUseCase
import io.module.map.ILocationListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val locationUseCase: LocationUseCase,
    private val weatherUseCase: WeatherUseCase,
) : AndroidViewModel(application) {

    var latestLocation: Location? = null

    /**
     * The data flow of the main screen.
     * */
    private val _mainStateFlow = MutableStateFlow<MainState>(MainState.Init)
    val mainStateFlow: StateFlow<MainState> get() = _mainStateFlow

    /**
     * The data flow of weather forecast.
     * */
    private val _weatherDataStateFlow = MutableStateFlow<WeatherDataState>(WeatherDataState.Init)
    val weatherDataStateFlow: StateFlow<WeatherDataState> get() = _weatherDataStateFlow

    companion object {
        private const val TAG = "MainViewModel"
    }

    init {
        accessDeviceLocation()
    }

    /**
     * Try to access the current location of the device first
     * */
    private fun accessDeviceLocation() {
        viewModelScope.launch {
            locationUseCase.getCurrentLocation.invoke(
                listener = object : ILocationListener {
                    override fun onAccessing() {
                        LogUtil.d(TAG, "[Access Device Location] Accessing...")
                        setState(_mainStateFlow, MainState.AccessingLocation)
                    }

                    override fun onAccessSucceed(location: Location) {
                        LogUtil.d(TAG, "[Access Device Location] Access succeed, (${location.latitude}, ${location.longitude})")
                        latestLocation = location
                        setState(_mainStateFlow, MainState.AccessLocationSucceed(location))
                    }

                    override fun onAccessFailed(errorMessage: String) {
                        LogUtil.e(TAG, "[Access Device Location] Access failed, errorMessage: $errorMessage")
                        setState(_mainStateFlow, MainState.AccessLocationFailed(null, errorMessage))
                    }
                }
            )
        }
    }

    /**
     * Fetch the latest weather info data from Remote-server.
     *
     * @param latitude
     * @param longitude
     * */
    fun getWeatherData(
        latitude: Double,
        longitude: Double
    ): StateFlow<NetworkResult<WeatherForecastDTO>> {
        return weatherUseCase
            .getWeatherData(
                latitude = latitude,
                longitude = longitude
            )
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5 * 1000L),
                initialValue = NetworkResult.Loading()
            )
            .also { stateFlow ->
                viewModelScope.launch {
                    stateFlow.collect { result ->
                        handleRemoteWeatherData(result)
                    }
                }
            }
    }

    private suspend fun handleRemoteWeatherData(result: NetworkResult<WeatherForecastDTO>) {
        when (result) {
            is NetworkResult.Loading -> {
                LogUtil.d(TAG, "[Handle Weather Data] Loading...")
                setState(_weatherDataStateFlow, WeatherDataState.Fetching)
            }

            is NetworkResult.Success -> {
                result.data?.also { dto ->
                    LogUtil.d(TAG, "[Handle Weather Data] Succeed, data: $dto")
                    setState(_weatherDataStateFlow, WeatherDataState.FetchSucceed(dto.toWeatherInfoModel()))
                    weatherUseCase.cacheWeatherData.invoke(dto.toWeatherEntity())
                } ?: {
                    LogUtil.w(TAG, "[Handle Weather Data] Succeed without data")
                    setState(_weatherDataStateFlow, WeatherDataState.NoWeatherData)
                }
            }

            is NetworkResult.Failed -> {
                val errorCode = result.code
                val errorMessage = result.message
                LogUtil.e(TAG, "[Handle Weather Data] Failed, ($errorCode, $errorMessage)")
                setState(_weatherDataStateFlow, WeatherDataState.FetchFailed(errorCode, errorMessage))
            }
        }
    }
}