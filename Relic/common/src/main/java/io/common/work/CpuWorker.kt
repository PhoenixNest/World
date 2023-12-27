package io.common.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.CpuUsageInfo
import android.os.HardwarePropertiesManager.DEVICE_TEMPERATURE_CPU
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.common.RelicSystemServiceManager.getSystemNotificationManager
import io.common.system.CpuUtil.emitCpuTemperatureFlow
import io.common.system.CpuUtil.emitCpuUsageInfoList
import io.common.system.CpuUtil.emitFanSpeedsList
import io.common.system.CpuUtil.getCpuUsageInfo
import io.common.system.CpuUtil.getFanSpeeds
import io.common.system.CpuUtil.getHardwareTemperatureByType
import io.common.util.LogUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@HiltWorker
class CpuWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    companion object {
        const val TAG = "CpuWorker"

        private const val NOTIFICATION_ID = 0
        private const val NOTIFICATION_CHANNEL_ID = "99"
        private const val NOTIFICATION_CHANNEL_NAME = TAG
        private const val NOTIFICATION_CHANNEL_DESC = TAG
        private const val DEFAULT_INTERVAL_MINUTES = 15L

        fun buildOneTimeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<CpuWorker>()
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setInitialDelay(200L, TimeUnit.MILLISECONDS)
                .build()
        }

        fun buildPeriodRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<CpuWorker>(
                repeatInterval = DEFAULT_INTERVAL_MINUTES,
                repeatIntervalTimeUnit = TimeUnit.MINUTES
            ).build()
        }
    }

    /* ======================== override ======================== */

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            /* notificationId = */ NOTIFICATION_ID,
            /* notification = */ defaultWorkerNotification(context)
        )
    }

    override suspend fun doWork(): Result {
        LogUtil.debug(TAG, "[Cpu Worker] Started...")
        return withContext(Dispatchers.IO) {
            try {
                // Cpu info
                val cpuUsageInfo: List<CpuUsageInfo> = getCpuUsageInfo(context)
                val cpuTemperature: Float = getHardwareTemperatureByType(
                    context = context,
                    type = DEVICE_TEMPERATURE_CPU
                )

                // If the device has fans
                val fanSpeeds: List<Float> = getFanSpeeds(context)

                val cpuUsageInfoUpdateResult: Boolean = async {
                    updateCpuUsageInfoFlow(cpuUsageInfo)
                }.await()

                val cpuTemperatureUpdateResult: Boolean = async {
                    updateCpuTemperatureFlow(cpuTemperature)
                }.await()

                val fanSpeedsUpdateResult: Boolean = async {
                    updateFanSpeedsFlow(fanSpeeds)
                }.await()

                if (cpuUsageInfoUpdateResult
                    && cpuTemperatureUpdateResult
                    || fanSpeedsUpdateResult
                ) {
                    LogUtil.debug(TAG, "[Cpu Worker] Succeed.")
                    Result.success()
                } else {
                    LogUtil.warning(TAG, "[Cpu Worker] Retry.")
                    Result.retry()
                }
            } catch (exception: Exception) {
                LogUtil.error(TAG, "[Cpu Worker] Error, message: ${exception.message}")
                Result.failure()
            }
        }
    }

    /* ======================== Logical ======================== */

    private fun defaultWorkerNotification(context: Context): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel: NotificationChannel = NotificationChannel(
                /* id = */ NOTIFICATION_CHANNEL_ID,
                /* name = */ NOTIFICATION_CHANNEL_NAME,
                /* importance = */ NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = NOTIFICATION_CHANNEL_DESC
            }

            val manager: NotificationManager? = getSystemNotificationManager(context)
            manager?.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(
            /* context = */ context,
            /* channelId = */ NOTIFICATION_CHANNEL_ID
        ).apply {
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
        }.build()
    }

    private fun updateCpuUsageInfoFlow(list: List<CpuUsageInfo?>?): Boolean {
        return emitCpuUsageInfoList(list)
    }

    private fun updateCpuTemperatureFlow(value: Float?): Boolean {
        return emitCpuTemperatureFlow(value)
    }

    private fun updateFanSpeedsFlow(list: List<Float?>): Boolean {
        return emitFanSpeedsList(list)
    }
}