package io.common.system

import android.content.Context
import android.os.CpuUsageInfo
import android.os.HardwarePropertiesManager
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
}