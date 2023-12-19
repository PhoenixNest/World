package io.dev.relic.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import io.common.util.LogUtil
import io.common.util.system.BatteryUtil

class PowerConnectionReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "PowerConnectionReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let {
            when (it) {
                Intent.ACTION_POWER_CONNECTED -> {
                    LogUtil.debug(TAG, "[Power Status] Connected")
                    BatteryUtil.emitChargingStatus(true)
                }

                Intent.ACTION_POWER_DISCONNECTED -> {
                    LogUtil.debug(TAG, "[Power Status] Disconnected")
                    BatteryUtil.emitChargingStatus(false)
                }

                Intent.ACTION_BATTERY_CHANGED -> {
                    LogUtil.debug(TAG, "[Battery Status] onChange")
                    val temperature: Int = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)
                    BatteryUtil.emitTemperature(temperature)
                }

                else -> {
                    //
                }
            }
        }
    }
}