package io.common.util.system

import android.content.Context
import android.view.Display
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.util.LogUtil
import io.common.util.system.SystemUtil.getWindowManager

object ScreenUtil {

    private const val TAG = "ScreenUtil"

    private var screenWidth: Int = UNKNOWN_VALUE_INT
    private var screenHeight: Int = UNKNOWN_VALUE_INT

    @Suppress("DEPRECATION")
    fun getScreenWidth(context: Context): Int {
        if (screenWidth != UNKNOWN_VALUE_INT) {
            return screenWidth
        }

        return try {
            screenWidth = getScreenDisplay(context)?.width ?: UNKNOWN_VALUE_INT
            screenWidth
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Screen Width] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_INT
        }
    }

    @Suppress("DEPRECATION")
    fun getScreenHeight(context: Context): Int {
        if (screenHeight != UNKNOWN_VALUE_INT) {
            return screenHeight
        }

        return try {
            screenHeight = getScreenDisplay(context)?.height ?: screenHeight
            screenHeight
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Screen Height] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_INT
        }
    }

    @Suppress("DEPRECATION")
    private fun getScreenDisplay(context: Context): Display? {
        return try {
            getWindowManager(context)?.defaultDisplay
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Screen Display] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

}