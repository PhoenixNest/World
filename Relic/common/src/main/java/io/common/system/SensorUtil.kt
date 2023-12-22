package io.common.system

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.common.RelicSystemServiceManager.getSystemSensorManager
import io.common.util.LogUtil

object SensorUtil {

    private const val TAG = "SensorUtil"
    private const val SAMPLING_PERIOD_US: Int = 2 * 1000

    fun init(context: Context) {
        getAllSensors(context)
    }

    private fun getAllSensors(context: Context) {
        try {
            val manager: SensorManager? = getSystemSensorManager(context)
            manager?.apply {
                val sensorList: List<Sensor> = getSensorList(Sensor.TYPE_ALL)
                if (sensorList.isNotEmpty()) {
                    for (sensor: Sensor in sensorList) {
                        LogUtil.debug(TAG, "[${sensor.name} Sensor] Detected.")
                        registerSensorEventListener(
                            context = context,
                            sensor = sensor
                        )
                    }
                }
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
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
                        debug(TAG, "[onSensorChanged] Name: $name")
                        debug(TAG, "[onSensorChanged] Type: $type")
                        debug(TAG, "[onSensorChanged] Vendor: $vendor")
                        debug(TAG, "[onSensorChanged] Version: $version")
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                //
            }
        }
    }
}