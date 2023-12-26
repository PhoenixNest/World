package io.common.system

import android.content.Context
import android.os.CpuUsageInfo
import android.os.HardwarePropertiesManager
import android.os.HardwarePropertiesManager.TEMPERATURE_CURRENT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_FLOAT
import io.common.RelicSystemServiceManager.getHardwarePropertiesManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking

object CpuUtil {

    private val cpuUsageInfoFlow: MutableStateFlow<List<CpuUsageInfo?>?> = MutableStateFlow(null)
    private val cpuTemperatureFlow: MutableStateFlow<Float?> = MutableStateFlow(null)
    private val fanSpeedsFlow: MutableStateFlow<List<Float?>?> = MutableStateFlow(null)

    fun emitCpuUsageInfoList(list: List<CpuUsageInfo?>?): Boolean {
        return runBlocking(Dispatchers.IO) {
            cpuUsageInfoFlow.tryEmit(list)
        }
    }

    fun emitCpuTemperatureFlow(value: Float?): Boolean {
        return runBlocking(Dispatchers.IO) {
            cpuTemperatureFlow.tryEmit(value)
        }
    }

    fun emitFanSpeedsList(list: List<Float?>?): Boolean {
        return runBlocking(Dispatchers.IO) {
            fanSpeedsFlow.tryEmit(list)
        }
    }

    fun getCpuUsageInfoFlow(): StateFlow<List<CpuUsageInfo?>?> {
        return cpuUsageInfoFlow
    }

    fun getCpuTemperatureFlow(): StateFlow<Float?> {
        return cpuTemperatureFlow
    }

    fun getFanSpeedsFlow(): StateFlow<List<Float?>?> {
        return fanSpeedsFlow
    }

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