package io.dev.relic.feature.rv

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import io.dev.relic.R
import io.dev.relic.feature.activities.main.MainActivity

class WeatherAppWidgetProvider : AppWidgetProvider() {

    /**
     * Called in response to the [AppWidgetManager.ACTION_APPWIDGET_UPDATE] and
     * [AppWidgetManager.ACTION_APPWIDGET_RESTORED] broadcasts when this AppWidget
     * provider is being asked to provide [RemoteViews][android.widget.RemoteViews]
     * for a set of AppWidgets.  Override this method to implement your own AppWidget functionality.
     *
     * {@more}
     *
     * @param context   The [Context][android.content.Context] in which this receiver is
     * running.
     * @param appWidgetManager A [AppWidgetManager] object you can call [                  ][AppWidgetManager.updateAppWidget] on.
     * @param appWidgetIds The appWidgetIds for which an update is needed.  Note that this
     * may be all of the AppWidget instances for this provider, or just
     * a subset of them.
     *
     * @see AppWidgetManager.ACTION_APPWIDGET_UPDATE
     */
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        // Perform this loop procedure for each widget that belongs to this provider.
        appWidgetIds?.forEach { appWidgetId ->
            // Create an Intent to launch ExampleActivity.
            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                /* context = */ context,
                /* requestCode = */  0,
                /* intent = */ Intent(context, MainActivity::class.java),
                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Get the layout for the widget and attach an onClick listener to the button.
            val views: RemoteViews = RemoteViews(
                context?.packageName,
                R.layout.appwidget_weather_layout
            ).apply {
                setOnClickPendingIntent(R.id.button, pendingIntent)
            }

            // Tell the AppWidgetManager to perform an update on the current widget.
            appWidgetManager?.updateAppWidget(appWidgetId, views)
        }
    }

    /**
     * Called in response to the [AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED]
     * broadcast when this widget has been layed out at a new size or its options changed via
     * [AppWidgetManager.updateAppWidgetOptions].
     *
     * {@more}
     *
     * @param context   The [Context][android.content.Context] in which this receiver is
     * running.
     * @param appWidgetManager A [AppWidgetManager] object you can call [                  ][AppWidgetManager.updateAppWidget] on.
     * @param appWidgetId The appWidgetId of the widget whose size changed.
     * @param newOptions The new options of the changed widget.
     *
     * @see AppWidgetManager.ACTION_APPWIDGET_OPTIONS_CHANGED
     */
    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }

    /**
     * Implements [BroadcastReceiver.onReceive] to dispatch calls to the various
     * other methods on AppWidgetProvider.
     *
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    override fun onReceive(
        context: Context?,
        intent: Intent?
    ) {
        super.onReceive(context, intent)
    }

    /**
     * Called in response to the [AppWidgetManager.ACTION_APPWIDGET_RESTORED] broadcast
     * when instances of this AppWidget provider have been restored from backup.  If your
     * provider maintains any persistent data about its widget instances, override this method
     * to remap the old AppWidgetIds to the new values and update any other app state that may
     * be relevant.
     *
     *
     * This callback will be followed immediately by a call to [.onUpdate] so your
     * provider can immediately generate new RemoteViews suitable for its newly-restored set
     * of instances.
     *
     *
     * In addition, you should set [AppWidgetManager.OPTION_APPWIDGET_RESTORE_COMPLETED]
     * to true indicate if a widget has been restored successfully from the provider's side.
     *
     * {@more}
     *
     * @param context
     * @param oldWidgetIds
     * @param newWidgetIds
     */
    override fun onRestored(
        context: Context?,
        oldWidgetIds: IntArray?,
        newWidgetIds: IntArray?
    ) {
        super.onRestored(context, oldWidgetIds, newWidgetIds)
    }

    /**
     * Called in response to the [AppWidgetManager.ACTION_APPWIDGET_ENABLED] broadcast when
     * the a AppWidget for this provider is instantiated.  Override this method to implement your
     * own AppWidget functionality.
     *
     * {@more}
     * When the last AppWidget for this provider is deleted,
     * [AppWidgetManager.ACTION_APPWIDGET_DISABLED] is sent by the AppWidget manager, and
     * [.onDisabled] is called.  If after that, an AppWidget for this provider is created
     * again, onEnabled() will be called again.
     *
     * @param context   The [Context][android.content.Context] in which this receiver is
     * running.
     *
     * @see AppWidgetManager.ACTION_APPWIDGET_ENABLED
     */
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
    }

    /**
     * Called in response to the [AppWidgetManager.ACTION_APPWIDGET_DISABLED] broadcast, which
     * is sent when the last AppWidget instance for this provider is deleted.  Override this method
     * to implement your own AppWidget functionality.
     *
     * {@more}
     *
     * @param context   The [Context][android.content.Context] in which this receiver is
     * running.
     *
     * @see AppWidgetManager.ACTION_APPWIDGET_DISABLED
     */
    override fun onDisabled(context: Context?) {
        super.onDisabled(context)
    }

    /**
     * Called in response to the [AppWidgetManager.ACTION_APPWIDGET_DELETED] broadcast when
     * one or more AppWidget instances have been deleted.  Override this method to implement
     * your own AppWidget functionality.
     *
     * {@more}
     *
     * @param context   The [Context][android.content.Context] in which this receiver is
     * running.
     * @param appWidgetIds The appWidgetIds that have been deleted from their host.
     *
     * @see AppWidgetManager.ACTION_APPWIDGET_DELETED
     */
    override fun onDeleted(
        context: Context?,
        appWidgetIds: IntArray?
    ) {
        super.onDeleted(context, appWidgetIds)
    }
}