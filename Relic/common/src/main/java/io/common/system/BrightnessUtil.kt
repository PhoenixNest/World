package io.common.system

import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.annotation.IntRange

object BrightnessUtil {

    private const val MAX_BRIGHTNESS_VALUE = 255
    private const val MODERNIZE_BRIGHTNESS_VALUE = (255 / 2)
    private const val MINIMIZE_BRIGHTNESS_VALUE = 1

    fun maximizeBrightness(context: Context) {
        changeScreenBrightness(
            context = context,
            targetBrightnessValue = MAX_BRIGHTNESS_VALUE
        )
    }

    fun modernizeBrightness(context: Context) {
        changeScreenBrightness(
            context = context,
            targetBrightnessValue = MODERNIZE_BRIGHTNESS_VALUE
        )
    }

    fun minimizeBrightness(context: Context) {
        changeScreenBrightness(
            context = context,
            targetBrightnessValue = MINIMIZE_BRIGHTNESS_VALUE
        )
    }


    /**
     * [How to Maximize/Minimize Screen Brightness Programmatically in Android?](https://www.geeksforgeeks.org/how-to-maximize-minimize-screen-brightness-programmatically-in-android/)
     *
     * @param context
     * @param targetBrightnessValue
     * */
    fun changeScreenBrightness(
        context: Context,
        @IntRange(from = 0, to = 255) targetBrightnessValue: Int
    ) {
        try {
            if (hasWriteSettingPermission(context)) {
                // Change the screen brightness change mode to manual.
                Settings.System.putInt(
                    /* cr = */ context.contentResolver,
                    /* name = */ Settings.System.SCREEN_BRIGHTNESS_MODE,
                    /* value = */ Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
                )
                // Apply the screen brightness value to the system, this will change
                // the value in Settings ---> Display ---> Brightness level.
                // It will also change the screen brightness for the device.
                Settings.System.putInt(
                    /* cr = */ context.contentResolver,
                    /* name = */ Settings.System.SCREEN_BRIGHTNESS,
                    /* value = */ targetBrightnessValue
                )
            } else {
                enableManualChangeSettings(context)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    /* ======================== System permission checker ======================== */

    /**
     * Check whether this app has android write settings permission.
     *
     * @param context
     * */
    private fun hasWriteSettingPermission(context: Context): Boolean {
        return Settings.System.canWrite(context)
    }

    /**
     * Let user change the write settings permission.
     *
     * @param context
     * */
    private fun enableManualChangeSettings(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        context.startActivity(intent)
    }

}