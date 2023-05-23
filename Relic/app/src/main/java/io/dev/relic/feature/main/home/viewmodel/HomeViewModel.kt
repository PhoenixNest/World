package io.dev.relic.feature.main.home.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.domain.repository.IWeatherDataRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val weatherDataRepository: IWeatherDataRepository
) : AndroidViewModel(application) {

    val homeUiState: HomeUiState by mutableStateOf(HomeUiState())

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private fun loadWeatherData(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            weatherDataRepository.getWeatherData(latitude, longitude)
        }
    }

}