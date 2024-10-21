package io.module.media.ui.viewmodel

import android.app.Application
import androidx.activity.ComponentActivity
import androidx.lifecycle.AndroidViewModel
import io.module.media.utils.MediaPermissionDetector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class CameraViewModel @Inject constructor(
    application: Application
) : AndroidViewModel(application) {

    private val _permissionFlow = MutableStateFlow<Boolean>(false)
    val permissionFlow: StateFlow<Boolean> get() = _permissionFlow

    companion object {
        private const val TAG = "CameraViewModel"
    }

    fun checkAndRequestCameraPermission(activity: ComponentActivity) {
        MediaPermissionDetector.PERMISSION_ARRAY_CAMERA
            .filter { permissionString ->
                // Filter the denied permission first.
                !MediaPermissionDetector.checkPermission(
                    context = activity,
                    requestPermission = permissionString
                )
            }.forEach { permissionString ->
                // Then, request the runtime permission one by one.
                MediaPermissionDetector.requestRuntimePermission(
                    activity = activity,
                    requestPermission = permissionString,
                    permissionListener = object : MediaPermissionDetector.MediaPermissionListener {
                        override fun onPermissionGranted() {
                            _permissionFlow.tryEmit(true)
                        }

                        override fun onPermissionDenied() {
                            _permissionFlow.tryEmit(false)
                        }
                    }
                )
            }
    }
}