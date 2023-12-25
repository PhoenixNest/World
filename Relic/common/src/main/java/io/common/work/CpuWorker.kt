package io.common.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.common.RelicSystemServiceManager.getSystemNotificationManager
import io.common.system.CpuUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@HiltWorker
class CpuWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted parameters: WorkerParameters
) : CoroutineWorker(context, parameters) {

    companion object {
        private const val TAG = "CpuWorker"

        private const val NOTIFICATION_ID = 0
        private const val NOTIFICATION_CHANNEL_ID = ""
        private const val NOTIFICATION_CHANNEL_NAME = ""
        private const val NOTIFICATION_CHANNEL_DESC = ""

        fun buildRequest(): PeriodicWorkRequest {
            return PeriodicWorkRequestBuilder<CpuWorker>(
                repeatInterval = 500L,
                repeatIntervalTimeUnit = TimeUnit.MICROSECONDS
            ).apply {
                setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
            }.build()
        }
    }

    private fun mCpuNotification(context: Context): Notification {
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

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            /* notificationId = */ NOTIFICATION_ID,
            /* notification = */ mCpuNotification(context)
        )
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                CpuUtil.apply {
                    getTotalCoresNumber(context)
                    getCpuUsageInfo(context)
                    getFanSpeeds(context)
                }
                Result.success()
            } catch (exception: Exception) {
                Result.failure()
            }
        }
    }
}