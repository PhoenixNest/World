package io.dev.relic.feature.page.home.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.core.data.network.monitor.IFetchDataMonitor
import io.dev.relic.domain.use_case.food_receipes.FoodRecipesUseCase
import io.dev.relic.domain.use_case.todo.TodoUseCase
import io.dev.relic.domain.use_case.weather.WeatherUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    application: Application,
    private val todoUseCase: TodoUseCase,
    private val weatherUseCase: WeatherUseCase,
    private val foodRecipesUseCase: FoodRecipesUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "HomeViewModel"
    }

    private fun fetchWeatherForecastData(
        latitude: Double,
        longitude: Double
    ) {
        viewModelScope.launch {
            weatherUseCase.fetchWeatherData.invoke(
                latitude = latitude,
                longitude = longitude,
                listener = object : IFetchDataMonitor {
                    override fun onFetching() {
                        TODO("Not yet implemented")
                    }

                    override fun <T> onFetchSucceed(dto: T) {
                        TODO("Not yet implemented")
                    }

                    override fun onFetchSucceedButNoData(errorMessage: String) {
                        TODO("Not yet implemented")
                    }

                    override fun onFetchFailed(errorMessage: String?) {
                        TODO("Not yet implemented")
                    }
                }
            )
        }
    }

}