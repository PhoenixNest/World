package io.module.media.wallpaper.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import io.module.media.R
import io.module.media.utils.MediaLogUtil
import java.text.SimpleDateFormat
import java.util.Date

class DefaultWallpaperChangeReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "WallpaperChangeReceiver"
    }

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast.  During this time you can use the other methods on
     * BroadcastReceiver to view/modify the current result values.  This method
     * is always called within the main thread of its process, unless you
     * explicitly asked for it to be scheduled on a different thread using
     * [android.content.Context.registerReceiver]. When it runs on the main
     * thread you should
     * never perform long-running operations in it (there is a timeout of
     * 10 seconds that the system allows before considering the receiver to
     * be blocked and a candidate to be killed). You cannot launch a popup dialog
     * in your implementation of onReceive().
     *
     *
     * **If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
     * then the object is no longer alive after returning from this
     * function.** This means you should not perform any operations that
     * return a result to you asynchronously. If you need to perform any follow up
     * background work, schedule a [android.app.job.JobService] with
     * [android.app.job.JobScheduler].
     *
     * If you wish to interact with a service that is already running and previously
     * bound using [bindService()][android.content.Context.bindService],
     * you can use [.peekService].
     *
     *
     * The Intent filters used in [android.content.Context.registerReceiver]
     * and in application manifests are *not* guaranteed to be exclusive. They
     * are hints to the operating system about how to find suitable recipients. It is
     * possible for senders to force delivery to specific recipients, bypassing filter
     * resolution.  For this reason, [onReceive()][.onReceive]
     * implementations should respond only to known actions, ignoring any unexpected
     * Intents that they may receive.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Suppress("DEPRECATION")
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.run {
            if (action == Intent.ACTION_WALLPAPER_CHANGED) {
                val currentTimeMillis = System.currentTimeMillis()
                val dateFormat = SimpleDateFormat.getInstance()
                val timeString = dateFormat.format(Date(currentTimeMillis))

                MediaLogUtil.d(TAG, "[Wallpaper Broadcast] Setup success.")
                showNotification(context, timeString)
            }
        }
    }

    private fun showNotification(
        context: Context?,
        timeString: String
    ) {
        if (context == null) {
            return
        }

        val notificationId = hashCode()
        val channelId = "ChangeWallpaper"
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle(context.getString(R.string.wallpaper_change_title))
            .setContentText(context.getString(R.string.wallpaper_change_desc, timeString))
            .setSmallIcon(R.drawable.ic_notifications)
            .setAutoCancel(true)
            .build()


        val notificationManager = getNotificationManager(context)
        notificationManager?.notify(notificationId, notification)
    }

    private fun getNotificationManager(context: Context): NotificationManager? {
        return try {
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }
}