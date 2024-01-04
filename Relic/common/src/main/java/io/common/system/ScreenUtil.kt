package io.common.system

import android.content.Context
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import io.common.RelicConstants.Common.UNKNOWN_VALUE_DOUBLE
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicSystemServiceManager.getWindowManager
import io.common.util.LogUtil
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.sqrt

object ScreenUtil {

    private const val TAG = "ScreenUtil"

    private var screenInch: Double = UNKNOWN_VALUE_DOUBLE
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
            LogUtil.e(TAG, "[Screen Width] Error, ${exception.message}")
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
            LogUtil.e(TAG, "[Screen Height] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_INT
        }
    }

    @Suppress("DEPRECATION")
    fun getScreenInch(context: Context): Double {
        if (screenInch != UNKNOWN_VALUE_DOUBLE) {
            return screenInch
        }

        return try {
            val display: Display = getScreenDisplay(context) ?: return UNKNOWN_VALUE_DOUBLE
            val displayMetrics = DisplayMetrics()
            val point = Point()

            display.getMetrics(displayMetrics)
            display.getRealSize(point)

            val pX: Int = point.x
            val pY: Int = point.y

            val xDpi: Float = displayMetrics.xdpi
            val yDpi: Float = displayMetrics.ydpi

            val vX: Float = (pX / xDpi)
            val vY: Float = (pY / yDpi)

            val result: Double = sqrt((vX * vX + vY * vY)).toDouble()
            screenInch = BigDecimal(result).setScale(1, RoundingMode.HALF_UP).toDouble()
            screenInch
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_DOUBLE
        }
    }

    @Suppress("DEPRECATION")
    private fun getScreenDisplay(context: Context): Display? {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                context.display
            } else {
                getWindowManager(context)?.defaultDisplay
            }
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Screen Display] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

}