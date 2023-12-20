package io.common.util.system

import android.content.Context
import android.hardware.Camera
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT

object CameraUtil {

    private const val TAG = "CameraUtil"

    private var frontCameraId: Int = UNKNOWN_VALUE_INT

    fun getFrontCameraId(context: Context): Int {
        if (frontCameraId != UNKNOWN_VALUE_INT) {
            return frontCameraId
        }

        return try {
            val totalNumbers: Int = Camera.getNumberOfCameras()
            val cameraInfo = Camera.CameraInfo()
            for (i: Int in 0 until totalNumbers) {
                Camera.getCameraInfo(i, cameraInfo)
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    frontCameraId = i
                    break
                }
            }

            frontCameraId
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_INT
        }
    }

    fun getCameraInfos() {
        val totalNumbers = Camera.getNumberOfCameras()
        for (i: Int in 0 until totalNumbers) {
            val cameraInfo = Camera.CameraInfo()
            Camera.getCameraInfo(i, cameraInfo)
        }
    }

}