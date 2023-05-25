package io.dev.relic.feature.main.unit.home.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.module.data.database.entity.TodoEntity
import io.dev.relic.core.module.data.database.mappers.TodoDataMapper.toTodoDataList
import io.dev.relic.core.module.location.RelicLocationTracker
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.repository.ITodoDataRepository
import io.dev.relic.domain.repository.IWeatherDataRepository
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val locationTracker: RelicLocationTracker,
    private val todoDataRepository: ITodoDataRepository,
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
        loadTodoDataFromDatabase()
        fetchRemoteWeatherData()
    }

    /* ======================== Local ======================== */

    /**
     * Read all todo info data according from database.
     * */
    private fun loadTodoDataFromDatabase() {
        viewModelScope.launch {
            // Update the ui state and emit it to the Ui layer.
            _homeUiState = _homeUiState.copy(
                isLoadingTodoData = true,
                todoDataList = null,
                errorMessageOfTodoInfo = null
            )
            _homeUiStateFlow.emit(_homeUiState)

            // Query todo data from database.
            todoDataRepository.readAllTodos().onEach { todoEntityList: List<TodoEntity> ->
                // Check the loading status of read action.
                if (todoEntityList.isNotEmpty()) {
                    // Update the ui state and emit it to the Ui layer.
                    _homeUiState = _homeUiState.copy(
                        isLoadingTodoData = false,
                        todoDataList = todoEntityList.toTodoDataList(),
                        errorMessageOfTodoInfo = null
                    )
                    _homeUiStateFlow.emit(_homeUiState)
                }
            }.launchIn(this)
        }
    }

    /* ======================== Remote ======================== */

    /**
     * Fetch the latest weather info data according to the current device location.
     * */
    private fun fetchRemoteWeatherData() {
        viewModelScope.launch {
            // Update the ui state and emit it to the Ui layer.
            _homeUiState = _homeUiState.copy(
                isLoadingWeatherData = true,
                weatherInfoModel = null,
                errorMessageOfWeatherInfo = null
            )
            _homeUiStateFlow.emit(_homeUiState)

            // Try to access the current location of the device first.
            locationTracker.getCurrentLocation()?.run {
                when (
                    // Fetch the latest weather data from remote-server.
                    val result: NetworkResult<WeatherInfoModel> = weatherDataRepository.getWeatherData(
                        latitude = latitude,
                        longitude = longitude
                    )
                ) {
                    is NetworkResult.Failed -> {
                        // Update the ui state and emit it to the Ui layer.
                        _homeUiState = _homeUiState.copy(
                            isLoadingWeatherData = false,
                            weatherInfoModel = null,
                            errorMessageOfWeatherInfo = result.message
                        )
                        _homeUiStateFlow.emit(_homeUiState)
                    }

                    is NetworkResult.Success -> {
                        // Update the ui state and emit it to the Ui layer.
                        _homeUiState = _homeUiState.copy(
                            isLoadingWeatherData = false,
                            weatherInfoModel = result.data,
                            errorMessageOfWeatherInfo = null
                        )
                        _homeUiStateFlow.emit(_homeUiState)
                    }
                }
            } ?: run {
                // Update the ui state and emit it to the Ui layer.
                _homeUiState = _homeUiState.copy(
                    isLoadingWeatherData = false,
                    errorMessageOfWeatherInfo = "Couldn't retrieve location."
                )
                _homeUiStateFlow.emit(_homeUiState)
            }
        }
    }
}