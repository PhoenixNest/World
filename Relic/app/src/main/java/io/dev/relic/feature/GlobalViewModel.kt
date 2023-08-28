package io.dev.relic.feature

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.dev.relic.domain.use_case.lcoation.LocationUseCase
import io.dev.relic.domain.use_case.lcoation.action.AccessCurrentLocation
import io.dev.relic.global.utils.LogUtil
import io.dev.relic.global.utils.ext.ViewModelExt.setState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class GlobalViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    @Inject
    lateinit var locationUseCase: LocationUseCase

    private val _globalStateFlow: MutableStateFlow<GlobalState> = MutableStateFlow(GlobalState.Init)
    val globalStateFlow: StateFlow<GlobalState> get() = _globalStateFlow

    companion object {
        private const val TAG = "FeatureViewModel"
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
                listener = object : AccessCurrentLocation.ILocationListener {
                    override fun onAccessing() {
                        LogUtil.debug(TAG, "[Access Device Location] Accessing...")
                        setState(
                            stateFlow = _globalStateFlow,
                            newState = GlobalState.AccessingLocation
                        )
                    }

                    override fun onAccessSucceed(location: Location) {
                        LogUtil.debug(
                            TAG,
                            "[Access Device Location] Access succeed, (${location.latitude}, ${location.longitude})"
                        )
                        setState(
                            stateFlow = _globalStateFlow,
                            newState = GlobalState.AccessLocationSucceed(location)
                        )
                    }

                    override fun onAccessFailed(errorMessage: String) {
                        LogUtil.debug(
                            TAG,
                            "[Access Device Location] Access failed, errorMessage: $errorMessage"
                        )
                        setState(
                            stateFlow = _globalStateFlow,
                            newState = GlobalState.AccessLocationFailed(
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