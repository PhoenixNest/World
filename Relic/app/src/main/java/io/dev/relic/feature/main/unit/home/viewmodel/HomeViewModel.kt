package io.dev.relic.feature.main.unit.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.module.data.network.api.model.weather.WeatherDTO
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.repository.IWeatherDataRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val weatherDataRepository: IWeatherDataRepository
) : AndroidViewModel(application) {

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

            // Emit Loading state to the Ui layer.
            _homeUiStateFlow.emit(
                HomeUiState(
                    isLoading = true,
                    weatherDataModel = null,
                    error = null
                )
            )

            when (
                val result: NetworkResult<WeatherDTO> = weatherDataRepository.getWeatherData(
                    latitude = latitude,
                    longitude = longitude
                )
            ) {
                is NetworkResult.Failed -> {
                    _homeUiStateFlow.emit(
                        HomeUiState(
                            isLoading = false,
                            weatherDataModel = null,
                            error = result.message
                        )
                    )
                }

                is NetworkResult.Success -> {
                    _homeUiStateFlow.emit(
                        HomeUiState(
                            isLoading = false,
                            weatherDataModel = null,
                            error = null
                        )
                    )
                }
            }
        }
    }

}