package io.common.services.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.CpuUsageInfo
import android.os.HardwarePropertiesManager
import io.common.system.CpuUtil
import io.common.system.CpuUtil.emitCpuTemperatureFlow
import io.common.system.CpuUtil.emitCpuUsageInfoList
import io.common.system.CpuUtil.emitFanSpeedsList
import io.common.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class CpuInfoReceiver : BroadcastReceiver() {

    private var onReceive: (() -> Unit)? = null

    companion object {
        private const val TAG = "CpuInfoReceiver"

        const val FIELD_BROADCAST_INTERVAL = 500L
        const val FIELD_BROADCAST_ACTION = "ACTION_CPU"
        const val FIELD_BROADCAST_REQUEST_CODE = 9999
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let { ctx ->
            intent?.action?.let { action ->
                if (action == FIELD_BROADCAST_ACTION) {
                    LogUtil.d(TAG, "[Cpu BroadcastReceiver] onReceive")
                    updateCpuInfo(ctx)
                    onReceive?.invoke()
                }
            }
        }
    }

    fun setOnReceive(block: () -> Unit): CpuInfoReceiver {
        return this.apply {
            onReceive = block
        }
    }

    private fun updateCpuInfo(context: Context) {
        try {
            runBlocking(Dispatchers.IO) {
                // Cpu info
                val cpuUsageInfo = CpuUtil.getCpuUsageInfo(context)
                val cpuTemperature = CpuUtil.getHardwareTemperatureByType(
                    context = context,
                    type = HardwarePropertiesManager.DEVICE_TEMPERATURE_CPU
                )

                // If the device has fans
                val fanSpeeds = CpuUtil.getFanSpeeds(context)

                val cpuUsageInfoUpdateResult = async {
                    updateCpuUsageInfoFlow(cpuUsageInfo)
                }.await()

                val cpuTemperatureUpdateResult = async {
                    updateCpuTemperatureFlow(cpuTemperature)
                }.await()

                val fanSpeedsUpdateResult = async {
                    updateFanSpeedsFlow(fanSpeeds)
                }.await()

                if (cpuUsageInfoUpdateResult
                    && cpuTemperatureUpdateResult
                    || fanSpeedsUpdateResult
                ) {
                    LogUtil.d(TAG, "[Update] Succeed.")
                } else {
                    LogUtil.d(TAG, "[Update] Failed.")
                }
            }
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Update] Error, message: ${exception.message}")
        }

    }

    private suspend fun updateCpuUsageInfoFlow(list: List<CpuUsageInfo?>?): Boolean {
        return emitCpuUsageInfoList(list)
    }

    private suspend fun updateCpuTemperatureFlow(value: Float?): Boolean {
        return emitCpuTemperatureFlow(value)
    }

    private suspend fun updateFanSpeedsFlow(list: List<Float?>): Boolean {
        return emitFanSpeedsList(list)
    }
}