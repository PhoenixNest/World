package io.dev.relic.feature.activities.main.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.common.ext.ViewModelExt.setState
import io.common.util.LogUtil
import io.dev.relic.feature.screens.main.MainState
import io.domain.use_case.lcoation.LocationUseCase
import io.module.map.ILocationListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val locationUseCase: LocationUseCase
) : AndroidViewModel(application) {

    var latestLocation: Location? = null

    private val _mainStateFlow: MutableStateFlow<MainState> = MutableStateFlow(MainState.Init)
    val mainStateFlow: StateFlow<MainState> get() = _mainStateFlow

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
            locationUseCase.accessCurrentLocation.invoke(
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
}