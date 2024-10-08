package io.common

import android.content.Context

/**
 * [Determine hardware availability](https://developer.android.com/training/permissions/declaring#determine-hardware-availability)
 * */
object RelicHardwareDetector {

    /**
     * Continue with the part of the app's workflow that requires the hardware,
     * such as: use PackageManager.FEATURE_CAMERA to check if device has Camera.
     * */
    interface RelicHardwareListener {

        fun withHardware()

        fun withoutHardware()

    }

    /**
     * Check whether your app is running on a device that has the specific hardware,
     * such as: Front camera.
     *
     * @param applicationContext
     * @param hardwareType
     * @param listener
     */
    fun checkDeviceHardware(
        applicationContext: Context,
        hardwareType: String,
        listener: RelicHardwareListener
    ) {
        val packageManager = applicationContext.packageManager
        if (packageManager.hasSystemFeature(hardwareType)) {
            listener.withHardware()
        } else {
            listener.withoutHardware()
        }
    }

}