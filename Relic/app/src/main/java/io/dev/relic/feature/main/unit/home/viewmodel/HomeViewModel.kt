package io.dev.relic.feature.main.unit.home.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.module.location.RelicLocationTracker
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.repository.IWeatherDataRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val locationTracker: RelicLocationTracker,
    private val weatherDataRepository: IWeatherDataRepository
) : AndroidViewModel(application) {

    private var _homeUiState: HomeUiState by mutableStateOf(value = HomeUiState())
    val homeUiState: HomeUiState get() = _homeUiState

    private var _homeUiStateFlow: MutableSharedFlow<HomeUiState> = MutableSharedFlow()
    val homeUiStateFlow: SharedFlow<HomeUiState> get() = _homeUiStateFlow

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private fun loadWeatherData(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            // Update the ui state and emit it to the Ui layer
            _homeUiState = _homeUiState.copy(
                isLoading = true,
                weatherInfo = null,
                error = null
            )
            _homeUiStateFlow.emit(_homeUiState)

            locationTracker.getCurrentLocation()?.run {
                when (
                    val result: NetworkResult<WeatherInfoModel> = weatherDataRepository.getWeatherData(
                        latitude = latitude,
                        longitude = longitude
                    )
                ) {
                    is NetworkResult.Failed -> {
                        // Update the ui state and emit it to the Ui layer
                        _homeUiState = _homeUiState.copy(
                            isLoading = false,
                            weatherInfo = null,
                            error = result.message
                        )
                        _homeUiStateFlow.emit(_homeUiState)
                    }

                    is NetworkResult.Success -> {
                        // Update the ui state and emit it to the Ui layer
                        _homeUiState = _homeUiState.copy(
                            isLoading = false,
                            weatherInfo = result.data,
                            error = null
                        )
                        _homeUiStateFlow.emit(_homeUiState)
                    }
                }
            } ?: run {
                _homeUiStateFlow.emit(
                    HomeUiState(
                        isLoading = false,
                        error = "Couldn't retrieve location."
                    )
                )
            }
        }
    }
}