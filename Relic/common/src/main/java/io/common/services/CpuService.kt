package io.common.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.os.SystemClock
import io.common.RelicSystemServiceManager.getAlarmManager
import io.common.services.receiver.CpuInfoReceiver
import io.common.services.receiver.CpuInfoReceiver.Companion.FIELD_BROADCAST_ACTION
import io.common.services.receiver.CpuInfoReceiver.Companion.FIELD_BROADCAST_INTERVAL
import io.common.services.receiver.CpuInfoReceiver.Companion.FIELD_BROADCAST_REQUEST_CODE

class CpuService : Service() {

    /**
     * Use the pending intent to send and register service.
     * */
    private var pendingIntent: PendingIntent? = null

    /**
     * Register a broadcast receiver to this service.
     *
     * @see CpuInfoReceiver
     * @see CpuInfoReceiver.updateCpuInfo
     * */
    private var receiver: BroadcastReceiver? = null

    companion object {
        private const val TAG = "CpuService"
    }

    /* ======================== override ======================== */

    override fun onCreate() {
        super.onCreate()
        initService(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()

        // Avoid OOM
        unregisterReceiver(receiver)
        pendingIntent = null
        receiver = null
    }

    /* ======================== Logical ======================== */

    private fun initService(context: Context) {
        initBroadcastReceiver(context)
        initPendingIntent(context)
        activeCpuAlarm(context)
    }

    private fun initBroadcastReceiver(context: Context) {
        receiver = CpuInfoReceiver().setOnReceive {
            reactiveCpuAlarm(context)
        }
    }

    private fun initPendingIntent(context: Context) {
        Intent().apply {
            action = FIELD_BROADCAST_ACTION
        }.let { intent ->
            pendingIntent = PendingIntent.getBroadcast(
                /* context = */ context,
                /* requestCode = */ FIELD_BROADCAST_REQUEST_CODE,
                /* intent = */ intent,
                /* flags = */ PendingIntent.FLAG_IMMUTABLE
            )
        }
    }

    /**
     * Active the default alarm.
     *
     * @param context
     * @param triggerTime  The interval time duration of the next alarm.
     * */
    private fun activeCpuAlarm(
        context: Context,
        triggerTime: Long = SystemClock.elapsedRealtime()
    ) {
        val alarmManager = getAlarmManager(context)
        pendingIntent?.let { intent ->
            alarmManager?.setExactAndAllowWhileIdle(
                /* type = */ AlarmManager.ELAPSED_REALTIME_WAKEUP,
                /* triggerAtMillis = */ triggerTime,
                /* operation = */ intent
            )
        }
    }

    /**
     * Re-active alarm when the previous one was completed.
     *
     * @param context
     * */
    private fun reactiveCpuAlarm(context: Context) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        val triggerTime = elapsedRealtime + FIELD_BROADCAST_INTERVAL
        activeCpuAlarm(context, triggerTime)
    }
}