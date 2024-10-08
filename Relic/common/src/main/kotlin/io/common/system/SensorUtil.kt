package io.common.system

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import io.common.RelicSystemServiceManager.getSystemSensorManager
import io.common.util.LogUtil

object SensorUtil {

    private const val TAG = "SensorUtil"
    private const val SAMPLING_PERIOD_US = 2 * 1000

    fun getAllSensors(context: Context): List<Sensor> {
        return try {
            val manager = getSystemSensorManager(context) ?: return emptyList()
            manager.getSensorList(Sensor.TYPE_ALL)
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[Sensor] Error, ${exception.message}")
            exception.printStackTrace()
            emptyList()
        }
    }

    fun registerSensorEventListener(
        context: Context,
        sensorList: List<Sensor>
    ) {
        if (sensorList.isNotEmpty()) {
            for (sensor in sensorList) {
                LogUtil.d(TAG, "[${sensor.name} Sensor] Detected.")
                registerSensorEventListener(
                    context = context,
                    sensor = sensor
                )
            }
        }
    }

    /**
     * [Monitoring Sensor Events](https://developer.android.google.cn/develop/sensors-and-location/sensors/sensors_overview#sensors-monitor)
     * */
    private fun registerSensorEventListener(
        context: Context,
        sensor: Sensor,
        samplingPeriodUs: Int = SAMPLING_PERIOD_US
    ) {
        getSystemSensorManager(context)?.registerListener(
            /* listener = */ getSensorEventListener(),
            /* sensor = */ sensor,
            /* samplingPeriodUs = */ samplingPeriodUs
        )
    }

    private fun getSensorEventListener(): SensorEventListener {
        return object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.sensor?.apply {
                    LogUtil.apply {
                        d(TAG, "[onSensorChanged] Name: $name")
                        d(TAG, "[onSensorChanged] Type: $type")
                        d(TAG, "[onSensorChanged] Vendor: $vendor")
                        d(TAG, "[onSensorChanged] Version: $version")
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                //
            }
        }
    }
}