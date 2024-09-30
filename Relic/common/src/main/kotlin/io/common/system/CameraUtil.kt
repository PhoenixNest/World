package io.common.system

import android.content.Context
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraMetadata
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.RelicSystemServiceManager.getCameraManager

object CameraUtil {

    private const val TAG = "CameraUtil"

    fun getCameraInfo(context: Context): String {
        return try {
            val manager = getCameraManager(context) ?: return UNKNOWN_VALUE_STRING
            manager.apply {
                val cameraIdList: Array<String> = cameraIdList
                if (cameraIdList.isNotEmpty()) {
                    for (cameraId in cameraIdList) {
                        val characteristics = manager.getCameraCharacteristics(cameraId)
                        val cameraLensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
                        val cameraCapabilities =
                            characteristics.get(CameraCharacteristics.REQUEST_AVAILABLE_CAPABILITIES)

                        // check if the selected camera device supports basic features
                        // ensures backward compatibility with the original Camera API
                        val isBackwardCompatible = cameraCapabilities?.contains(
                            CameraMetadata.REQUEST_AVAILABLE_CAPABILITIES_BACKWARD_COMPATIBLE
                        ) ?: false
                    }
                }
            }
            UNKNOWN_VALUE_STRING
        } catch (exception: CameraAccessException) {
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }
}