package io.common

import android.app.Activity
import android.app.ActivityManager
import android.app.NotificationManager
import android.content.Context
import android.hardware.SensorManager
import android.hardware.camera2.CameraManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.BatteryManager
import android.os.HardwarePropertiesManager
import android.os.health.SystemHealthManager
import android.telephony.TelephonyManager
import android.view.WindowManager
import io.common.util.LogUtil

object RelicSystemServiceManager {

    private const val TAG = "RelicSystemManager"

    @Suppress("UNCHECKED_CAST")
    fun <T> getManagerByType(
        context: Context,
        type: String
    ): T? {
        return try {
            context.getSystemService(type) as T
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

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
            context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Battery Manager] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    /**
     * [ConnectivityManager](https://developer.android.google.cn/reference/kotlin/android/net/ConnectivityManager?hl=en)
     *
     * @param context
     * */
    fun getConnectivityManager(context: Context): ConnectivityManager? {
        return try {
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Connectivity Manager] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    /**
     * [CameraManager](https://developer.android.google.cn/reference/kotlin/android/hardware/camera2/CameraManager?hl=en)
     *
     * [Camera2 overview](https://developer.android.google.cn/training/camera2)
     *
     * @param context
     * */
    fun getCameraManager(context: Context): CameraManager? {
        return try {
            context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Camera Manager] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    /**
     * [HardwarePropertiesManager](https://developer.android.google.cn/reference/kotlin/android/os/HardwarePropertiesManager?hl=en)
     *
     * @param context
     * */
    fun getHardwarePropertiesManager(context: Context): HardwarePropertiesManager? {
        return try {
            context.getSystemService(Context.HARDWARE_PROPERTIES_SERVICE) as HardwarePropertiesManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Hardware Properties Manager] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    /**
     * [Android Health](https://source.android.com/docs/core/perf/health)
     *
     * @param context
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

    fun getSystemNotificationManager(context: Context): NotificationManager? {
        return try {
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Notification] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    /**
     * [Sensors Overview](https://developer.android.google.cn/develop/sensors-and-location/sensors/sensors_overview)
     *
     * @param context
     * */
    fun getSystemSensorManager(context: Context): SensorManager? {
        return try {
            context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Sensor Manager] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    fun getTelephonyManager(context: Context): TelephonyManager? {
        return try {
            context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        } catch (exception: Exception) {
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