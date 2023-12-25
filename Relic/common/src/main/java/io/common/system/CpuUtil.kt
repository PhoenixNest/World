package io.common.system

import android.content.Context
import android.os.CpuUsageInfo
import android.os.HardwarePropertiesManager
import android.os.HardwarePropertiesManager.TEMPERATURE_CURRENT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_FLOAT
import io.common.RelicSystemServiceManager.getHardwarePropertiesManager

object CpuUtil {

    fun getTotalCoresNumber(context: Context): Int {
        return getCpuUsageInfo(context).size
    }

    fun getCpuUsageInfo(context: Context): List<CpuUsageInfo> {
        return try {
            val manager: HardwarePropertiesManager =
                getHardwarePropertiesManager(context) ?: return emptyList()
            return manager.cpuUsages.toList()
        } catch (exception: Exception) {
            exception.printStackTrace()
            emptyList()
        }
    }

    fun getFanSpeeds(context: Context): List<Float> {
        return try {
            val manager: HardwarePropertiesManager =
                getHardwarePropertiesManager(context) ?: return emptyList()
            manager.fanSpeeds.toList()
        } catch (exception: Exception) {
            exception.printStackTrace()
            emptyList()
        }
    }

    fun getHardwareTemperatureByType(
        context: Context,
        type: Int
    ): Float {
        return try {
            val manager: HardwarePropertiesManager =
                getHardwarePropertiesManager(context) ?: return UNKNOWN_VALUE_FLOAT
            manager.getDeviceTemperatures(
                /* type = */ type,
                /* source = */ TEMPERATURE_CURRENT
            )
            UNKNOWN_VALUE_FLOAT
        } catch (exception: Exception) {
            exception.printStackTrace()
            UNKNOWN_VALUE_FLOAT
        }
    }
}