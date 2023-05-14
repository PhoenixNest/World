package io.dev.relic.core.module.device

import android.content.Context
import android.content.pm.PackageManager

/**
 * Reference link: [Determine hardware availability](https://developer.android.com/training/permissions/declaring#determine-hardware-availability)
 * */
object DeviceHardwareDetector {

    /**
     * Continue with the part of the app's workflow that requires the hardware,
     * such as: use PackageManager.FEATURE_CAMERA to check if device has Camera.
     * */
    interface HardwareListener {

        fun withHardware()

        fun withoutHardware()

    }

    /**
     * Check whether your app is running on a device that has the specific hardware,
     * such as: Front camera.
     *
     * @param applicationContext
     * @param hardwareType
     * @param hardwareListener
     */
    fun checkDeviceHardware(
        applicationContext: Context,
        hardwareType: String,
        hardwareListener: HardwareListener
    ) {
        val packageManager: PackageManager = applicationContext.packageManager
        if (packageManager.hasSystemFeature(hardwareType)) {
            hardwareListener.withHardware()
        } else {
            hardwareListener.withoutHardware()
        }
    }

}