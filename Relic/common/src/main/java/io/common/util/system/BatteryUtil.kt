package io.common.util.system

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build
import io.common.RelicConstants.Common.UNKNOWN_VALUE_FLOAT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_LONG
import io.common.util.LogUtil
import io.common.util.system.SystemUtil.getBatteryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking

object BatteryUtil {

    private const val TAG = "BatteryUtil"

    private val chargingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val temperatureFlow: MutableStateFlow<Int> = MutableStateFlow(0)

    enum class ChargeType(val type: Int) {
        USB(BatteryManager.BATTERY_PLUGGED_USB),
        AC(BatteryManager.BATTERY_PLUGGED_AC),
        WIRELESS(BatteryManager.BATTERY_PLUGGED_WIRELESS),
        UNKNOWN(-1)
    }

    fun emitChargingStatus(isCharging: Boolean) {
        runBlocking(Dispatchers.IO) {
            chargingFlow.emit(isCharging)
        }
    }

    fun emitTemperature(value: Int) {
        runBlocking(Dispatchers.IO) {
            temperatureFlow.emit(value)
        }
    }

    fun getChargingFlow(): StateFlow<Boolean> {
        return chargingFlow
    }

    fun getTemperatureFlow(): StateFlow<Int> {
        return temperatureFlow
    }

    fun isCharging(context: Context): Boolean {
        return try {
            getBatteryManager(context)?.isCharging ?: false
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[isCharging] Error, ${exception.message}")
            exception.printStackTrace()
            false
        }
    }

    fun getRemainChargeTime(context: Context): Long {
        return try {
            var result: Long = UNKNOWN_VALUE_LONG
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val leftTime: Long = getBatteryManager(context)?.computeChargeTimeRemaining() ?: -1
                result = leftTime / 1000
            }
            result
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Remain Charging Time] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }

    fun getBatteryChargeType(context: Context): ChargeType? {
        return try {
            val chargePlug: Int = getBatteryStatus(context)?.getIntExtra(
                /* name = */ BatteryManager.EXTRA_PLUGGED,
                /* defaultValue = */ ChargeType.UNKNOWN.type
            ) ?: ChargeType.UNKNOWN.type

            when (chargePlug) {
                BatteryManager.BATTERY_PLUGGED_USB -> ChargeType.USB
                BatteryManager.BATTERY_PLUGGED_AC -> ChargeType.AC
                BatteryManager.BATTERY_PLUGGED_WIRELESS -> ChargeType.WIRELESS
                else -> ChargeType.UNKNOWN
            }
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Charge Type] Error, ${exception.message}")
            exception.printStackTrace()
            ChargeType.UNKNOWN
        }
    }

    fun getBatteryLevel(context: Context): Int {
        return try {
            val result: Int? = getBatteryManager(context)?.getIntProperty(
                BatteryManager.BATTERY_PROPERTY_CAPACITY
            )
            result ?: UNKNOWN_VALUE_INT
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Battery Level] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_INT
        }
    }

    /**
     * [Determine the current battery level](https://developer.android.com/training/monitoring-device-state/battery-monitoring#CurrentLevel)
     *
     * In some cases it's also useful to determine the current battery level. You may choose to reduce the rate of your background updates if the battery charge is below a certain level.
     *
     * You can find the current battery charge by extracting the current battery level and scale from the battery status intent as shown here:
     *
     * @param context
     * */
    fun getCurrentBatteryLevel(context: Context): Float {
        return try {
            var result: Float = UNKNOWN_VALUE_FLOAT
            getBatteryStatus(context)?.let {
                val level: Int = it.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
                val scale: Int = it.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
                result = level * 100 / scale.toFloat()
            }
            result
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Current Battery Level] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_FLOAT
        }
    }

    fun getBatteryEnergyCounter(context: Context): Int {
        return try {
            val result: Int? = getBatteryManager(context)?.getIntProperty(
                BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER
            )
            result ?: UNKNOWN_VALUE_INT
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Battery Energy Counter] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_INT
        }
    }

    /**
     * [Determine the current charging state](https://developer.android.com/training/monitoring-device-state/battery-monitoring#DetermineChargeState)
     *
     * You can extract both the current charging status and, if the device is being charged, whether it's charging via USB or AC charger:
     *
     * @param context
     * */
    private fun getBatteryStatus(context: Context): Intent? {
        return try {
            IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
                context.registerReceiver(
                    /* receiver = */ null,
                    /* filter = */ it
                )
            }
        } catch (exception: Exception) {
            LogUtil.error(TAG, "[Battery Status] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

}