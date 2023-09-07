package io.dev.relic.feature.activities.main.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.domain.use_case.lcoation.LocationUseCase
import io.dev.relic.domain.use_case.lcoation.action.AccessCurrentLocation
import io.dev.relic.feature.MainState
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.ViewModelExt.setState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val locationUseCase: LocationUseCase
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "MainViewModel"
    }

    private val _mainStateFlow: MutableStateFlow<MainState> = MutableStateFlow(MainState.Init)
    val mainStateFlow: StateFlow<MainState> get() = _mainStateFlow

    init {
        accessDeviceLocation()
    }

    /**
     * Try to access the current location of the device first
     * */
    private fun accessDeviceLocation() {
        viewModelScope.launch {
            locationUseCase.accessCurrentLocation.invoke(
                listener = object : AccessCurrentLocation.ILocationListener {
                    override fun onAccessing() {
                        LogUtil.debug(TAG, "[Access Device Location] Accessing...")
                        setState(
                            stateFlow = _mainStateFlow,
                            newState = MainState.AccessingLocation
                        )
                    }

                    override fun onAccessSucceed(location: Location) {
                        LogUtil.debug(TAG, "[Access Device Location] Access succeed, (${location.latitude}, ${location.longitude})")
                        setState(
                            stateFlow = _mainStateFlow,
                            newState = MainState.AccessLocationSucceed(location)
                        )
                    }

                    override fun onAccessFailed(errorMessage: String) {
                        LogUtil.debug(TAG, "[Access Device Location] Access failed, errorMessage: $errorMessage")
                        setState(
                            stateFlow = _mainStateFlow,
                            newState = MainState.AccessLocationFailed(
                                errorCode = null,
                                errorMessage = errorMessage
                            )
                        )
                    }
                }
            )
        }
    }

}