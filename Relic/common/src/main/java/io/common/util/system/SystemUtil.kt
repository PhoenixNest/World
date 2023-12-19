package io.common.util.system

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.Build
import android.os.health.SystemHealthManager
import android.view.WindowManager
import io.common.RelicConstants
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.util.LogUtil

object SystemUtil {

    private const val TAG = "SystemInfoUtil"

    /* ======================== Common ======================== */

    fun getBrandInfo(context: Context): String {
        return try {
            Build.BRAND ?: UNKNOWN_VALUE_STRING
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Brand] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

    fun getPhoneModelInfo(): String {
        return try {
            Build.MODEL ?: UNKNOWN_VALUE_STRING
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Phone Model] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

    fun getOSInfo(): String {
        return try {
            "${RelicConstants.Common.ANDROID} ${Build.VERSION.RELEASE}"
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[OS] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

    fun getAvailableProcessors(): Int {
        return Runtime.getRuntime().availableProcessors()
    }

    fun getTotalJVMMemory(): Long {
        return try {
            Runtime.getRuntime().totalMemory()
        } catch (exception: Exception) {
            Runtime.getRuntime().maxMemory()
        }
    }

    fun getJVMFreeMemory(): Long {
        return Runtime.getRuntime().freeMemory()
    }

    /* ======================== Manager ======================== */

    fun getActivityManger(context: Context): ActivityManager? {
        return try {
            context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

    /**
     * [Monitor the Battery Level and Charging State](https://developer.android.com/training/monitoring-device-state/battery-monitoring)
     *
     * @param context
     * */
    fun getBatteryManager(context: Context): BatteryManager? {
        return try {
            return (context.getSystemService(Context.BATTERY_SERVICE)) as BatteryManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Battery Manager] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    /**
     * [Android Health](https://source.android.com/docs/core/perf/health)
     * */
    fun getSystemHealthManager(context: Context): SystemHealthManager? {
        return try {
            context.getSystemService(Context.SYSTEM_HEALTH_SERVICE) as SystemHealthManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[System Health] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    fun getWifiManager(context: Context): WifiManager? {
        return try {
            (context.getSystemService(Context.WIFI_SERVICE)) as WifiManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Wifi manager] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    fun getWindowManager(context: Context): WindowManager? {
        return try {
            return (context as Activity).windowManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Window manager] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

}