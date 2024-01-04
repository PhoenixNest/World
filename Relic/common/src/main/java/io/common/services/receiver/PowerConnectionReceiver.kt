package io.common.services.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import io.common.system.BatteryUtil.emitChargingStatus
import io.common.system.BatteryUtil.emitChargingVoltage
import io.common.system.BatteryUtil.emitTemperature
import io.common.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

class PowerConnectionReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "PowerConnectionReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let {
            when (it) {
                Intent.ACTION_POWER_CONNECTED -> {
                    LogUtil.d(TAG, "[Power Status] Connected")
                    updateChargingStatus(true)
                }

                Intent.ACTION_POWER_DISCONNECTED -> {
                    LogUtil.d(TAG, "[Power Status] Disconnected")
                    updateChargingStatus(false)
                }

                Intent.ACTION_BATTERY_CHANGED -> {
                    LogUtil.d(TAG, "[Power Status] onChange")
                    onBatteryChanged(intent)
                }

                else -> {
                    //
                }
            }
        }
    }

    private fun onBatteryChanged(intent: Intent) {
        try {
            runBlocking(Dispatchers.IO) {
                val temperature: Int = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
                val voltage: Int = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0)

                val batteryChargingVoltageUpdateResult: Boolean = async {
                    updateChargingVoltageFlow(voltage)
                }.await()

                val batteryTemperatureUpdateResult: Boolean = async {
                    updateBatteryTemperatureFlow(temperature)
                }.await()

                if (batteryChargingVoltageUpdateResult
                    && batteryTemperatureUpdateResult
                ) {
                    LogUtil.d(TAG, "[Update] Succeed.")
                } else {
                    LogUtil.w(TAG, "[Update] Failed.")
                }
            }
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Update] Error, message: ${exception.message}")
            exception.printStackTrace()
        }
    }

    private fun updateChargingStatus(isCharging: Boolean): Boolean {
        return runBlocking(Dispatchers.IO) {
            emitChargingStatus(isCharging)
        }
    }

    private suspend fun updateChargingVoltageFlow(value: Int): Boolean {
        return emitChargingVoltage(value)
    }

    private suspend fun updateBatteryTemperatureFlow(value: Int): Boolean {
        return emitTemperature(value)
    }
}