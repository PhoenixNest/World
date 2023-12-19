package io.common.util.system

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiInfo
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.util.LogUtil
import io.common.util.system.SystemUtil.getWifiManager

object NetworkUtil {

    private const val TAG = "NetworkUtil"

    private var wifiInfo: WifiInfo? = null
    private var deviceMacAddress: String = UNKNOWN_VALUE_STRING

    @Suppress("DEPRECATION")
    private fun getConnectionInfo(context: Context): WifiInfo? {
        if (wifiInfo != null) {
            return wifiInfo
        }

        return try {
            getWifiManager(context)?.connectionInfo
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Connection] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

    @SuppressLint("HardwareIds")
    fun getMacAddressInfo(context: Context): String {
        if (deviceMacAddress != UNKNOWN_VALUE_STRING) {
            return deviceMacAddress
        }

        return try {
            getConnectionInfo(context)?.macAddress ?: UNKNOWN_VALUE_STRING
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Mac Address] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_STRING
        }
    }

}