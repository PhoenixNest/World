package io.dev.relic.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.common.util.LogUtil

class BatteryLevelReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "BatteryLevelReceiver"
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.action?.let {
            when (it) {
                Intent.ACTION_BATTERY_LOW -> {
                    LogUtil.debug(TAG, "[Current Level] Low")
                }

                Intent.ACTION_BATTERY_OKAY -> {
                    LogUtil.debug(TAG, "[Current Level] Okay")
                }

                else -> {
                    //
                }
            }
        }
    }
}