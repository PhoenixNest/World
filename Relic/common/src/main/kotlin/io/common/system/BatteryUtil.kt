package io.common.system

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager.BATTERY_PLUGGED_AC
import android.os.BatteryManager.BATTERY_PLUGGED_USB
import android.os.BatteryManager.BATTERY_PLUGGED_WIRELESS
import android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY
import android.os.BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER
import android.os.BatteryManager.EXTRA_LEVEL
import android.os.BatteryManager.EXTRA_PLUGGED
import android.os.BatteryManager.EXTRA_SCALE
import android.os.Build
import io.common.RelicConstants.Common.UNKNOWN_VALUE_FLOAT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_LONG
import io.common.RelicSystemServiceManager.getBatteryManager
import io.common.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

object BatteryUtil {

    private const val TAG = "BatteryUtil"

    private val chargingFlow = MutableStateFlow(false)
    private val voltageFlow = MutableStateFlow(0)
    private val temperatureFlow = MutableStateFlow(0)

    enum class ChargeType(val type: Int) {
        USB(BATTERY_PLUGGED_USB),
        AC(BATTERY_PLUGGED_AC),
        WIRELESS(BATTERY_PLUGGED_WIRELESS),
        UNKNOWN(-1)
    }

    suspend fun emitChargingStatus(isCharging: Boolean): Boolean {
        return withContext(Dispatchers.IO) {
            chargingFlow.tryEmit(isCharging)
        }
    }

    suspend fun emitChargingVoltage(value: Int): Boolean {
        return withContext(Dispatchers.IO) {
            voltageFlow.tryEmit(value)
        }
    }

    suspend fun emitTemperature(value: Int): Boolean {
        return withContext(Dispatchers.IO) {
            temperatureFlow.tryEmit(value)
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
            LogUtil.e(TAG, "[isCharging] Error, ${exception.message}")
            exception.printStackTrace()
            false
        }
    }

    fun getRemainChargeTime(context: Context): Long {
        return try {
            var result = UNKNOWN_VALUE_LONG
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val leftTime = getBatteryManager(context)?.computeChargeTimeRemaining() ?: -1
                result = leftTime / 1000
            }
            result
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Remain Charging Time] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_LONG
        }
    }

    fun getBatteryChargeType(context: Context): ChargeType? {
        return try {
            val intent = getBatteryStatusIntent(context) ?: return ChargeType.UNKNOWN
            val chargePlug = intent.getIntExtra(
                /* name = */ EXTRA_PLUGGED,
                /* defaultValue = */ ChargeType.UNKNOWN.type
            )

            when (chargePlug) {
                BATTERY_PLUGGED_USB -> ChargeType.USB
                BATTERY_PLUGGED_AC -> ChargeType.AC
                BATTERY_PLUGGED_WIRELESS -> ChargeType.WIRELESS
                else -> ChargeType.UNKNOWN
            }
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Charge Type] Error, ${exception.message}")
            exception.printStackTrace()
            ChargeType.UNKNOWN
        }
    }

    fun getBatteryLevel(context: Context): Int {
        return try {
            val manager = getBatteryManager(context) ?: return UNKNOWN_VALUE_INT
            val result = manager.getIntProperty(BATTERY_PROPERTY_CAPACITY)
            result
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Battery Level] Error, ${exception.message}")
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
            var result: Float
            val intent = getBatteryStatusIntent(context) ?: return UNKNOWN_VALUE_FLOAT
            intent.apply {
                val level: Int = getIntExtra(EXTRA_LEVEL, -1)
                val scale: Int = getIntExtra(EXTRA_SCALE, -1)
                result = level * 100 / scale.toFloat()
            }
            result
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Current Battery Level] Error, ${exception.message}")
            exception.printStackTrace()
            UNKNOWN_VALUE_FLOAT
        }
    }

    fun getBatteryEnergyCounter(context: Context): Int {
        return try {
            val manager = getBatteryManager(context) ?: return UNKNOWN_VALUE_INT
            val result = manager.getIntProperty(BATTERY_PROPERTY_ENERGY_COUNTER)
            result
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Battery Energy Counter] Error, ${exception.message}")
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
    private fun getBatteryStatusIntent(context: Context): Intent? {
        return try {
            IntentFilter(Intent.ACTION_BATTERY_CHANGED).let {
                context.registerReceiver(
                    /* receiver = */ null,
                    /* filter = */ it
                )
            }
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Battery Status] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }
}