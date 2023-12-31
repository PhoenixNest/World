package io.common.system

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.telephony.TelephonyManager
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.RelicPermissionDetector.Native.checkPermission
import io.common.RelicSystemServiceManager.getConnectivityManager
import io.common.RelicSystemServiceManager.getTelephonyManager
import io.common.RelicSystemServiceManager.getWifiManager
import io.common.util.LogUtil
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object NetworkUtil {

    private const val TAG = "NetworkUtil"

    private var wifiInfo: WifiInfo? = null
    private var deviceMacAddress: String = UNKNOWN_VALUE_STRING

    private const val UNKNOWN_GATE_WAY = "0.0.0.0"

    enum class NetworkType {
        UNKNOWN,
        WIFI,
        CELLULAR_2G,
        CELLULAR_3G,
        CELLULAR_4G,
        CELLULAR_5G
    }

    @Suppress("DEPRECATION")
    fun getCurrentNetworkType(context: Context): NetworkType {
        return try {
            val connectivityManager: ConnectivityManager =
                getConnectivityManager(context) ?: return NetworkType.UNKNOWN

            // The current network info may be none.
            val networkInfo: NetworkInfo =
                connectivityManager.activeNetworkInfo ?: return NetworkType.UNKNOWN

            // Check the current network capability
            if (!networkInfo.isAvailable) {
                return NetworkType.UNKNOWN
            }

            val telephonyManager: TelephonyManager =
                getTelephonyManager(context) ?: return NetworkType.UNKNOWN

            val currentNetworkType: NetworkType = checkCurrentNetworkType(
                context = context,
                connectivityManager = connectivityManager,
                telephonyManager = telephonyManager
            )
            currentNetworkType
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkType.UNKNOWN
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

    fun getGateWay(): String {
        return try {
            val strings: List<String>
            val process: Process = Runtime.getRuntime().exec("ip route list table 0")
            val bufferedReader = BufferedReader(InputStreamReader(process.inputStream))
            val string: String = bufferedReader.readLine()
            bufferedReader.close()
            strings = string.split("\r+")
            strings[2]
        } catch (exception: IOException) {
            exception.printStackTrace()
            UNKNOWN_GATE_WAY
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_GATE_WAY
        }
    }

    @Suppress("DEPRECATION")
    private fun checkCurrentNetworkType(
        context: Context,
        connectivityManager: ConnectivityManager,
        telephonyManager: TelephonyManager
    ): NetworkType {
        return try {

            /* ======================== Wifi ======================== */

            val wifiNetwork: NetworkInfo =
                connectivityManager.getNetworkInfo(TYPE_WIFI) ?: return NetworkType.UNKNOWN

            val state: NetworkInfo.State =
                wifiNetwork.state ?: return NetworkType.UNKNOWN

            if (state == NetworkInfo.State.CONNECTED
                || state == NetworkInfo.State.CONNECTING
            ) return NetworkType.WIFI

            /* ======================== CELLULARphone ======================== */

            // Check whether we already have the permission to read user's phone state.
            val hasPermission: Boolean = checkPermission(
                context = context,
                requestPermission = Manifest.permission.READ_PHONE_STATE
            )

            if (hasPermission) {
                when (telephonyManager.networkType) {
                    // 2G
                    TelephonyManager.NETWORK_TYPE_GPRS,
                    TelephonyManager.NETWORK_TYPE_CDMA,
                    TelephonyManager.NETWORK_TYPE_EDGE,
                    TelephonyManager.NETWORK_TYPE_1xRTT,
                    TelephonyManager.NETWORK_TYPE_IDEN -> NetworkType.CELLULAR_2G
                    // 3G
                    TelephonyManager.NETWORK_TYPE_EVDO_0,
                    TelephonyManager.NETWORK_TYPE_EVDO_A,
                    TelephonyManager.NETWORK_TYPE_EVDO_B,
                    TelephonyManager.NETWORK_TYPE_HSDPA,
                    TelephonyManager.NETWORK_TYPE_HSUPA,
                    TelephonyManager.NETWORK_TYPE_HSPA,
                    TelephonyManager.NETWORK_TYPE_EHRPD,
                    TelephonyManager.NETWORK_TYPE_HSPAP -> NetworkType.CELLULAR_3G
                    // 4G
                    TelephonyManager.NETWORK_TYPE_LTE -> NetworkType.CELLULAR_4G
                    // 5G
                    TelephonyManager.NETWORK_TYPE_NR -> NetworkType.CELLULAR_5G
                    else -> NetworkType.UNKNOWN
                }
            } else {
                NetworkType.UNKNOWN
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkType.UNKNOWN
        }
    }

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
}