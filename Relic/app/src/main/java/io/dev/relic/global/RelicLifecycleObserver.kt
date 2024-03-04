package io.dev.relic.global

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import io.common.util.LogUtil
import io.core.datastore.RelicDatastoreCenter.readSyncData
import io.core.datastore.RelicDatastoreCenter.writeSyncData
import io.core.datastore.preference_keys.SystemPreferenceKeys.KEY_IS_FIRST_COLD_START
import io.dev.relic.feature.activities.deeplink.DeepLinkActivity
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.activities.splash.SplashActivity

/**
 * [App startup time](https://developer.android.com/topic/performance/vitals/launch-time)
 * */
object RelicLifecycleObserver : DefaultLifecycleObserver, ActivityLifecycleCallbacks {

    private const val TAG = "RelicLifecycleObserver"

    /**
     * [Cold start](https://developer.android.com/topic/performance/vitals/launch-time#cold)
     * */
    var isFirstColdStart = dataStoreAppFirstStart
        private set

    /**
     * [Warm start](https://developer.android.com/topic/performance/vitals/launch-time#warm)
     * */
    var isWarmStart = false
        private set

    /**
     * [Hot start](https://developer.android.com/topic/performance/vitals/launch-time#hot)
     * */
    var isHotStart = false
        private set

    /**
     * Check if the app is first open in datastore.
     * */
    private val dataStoreAppFirstStart
        get() = readSyncData(KEY_IS_FIRST_COLD_START, true)

    /**
     * Check whether the current App is running in the foreground.
     * */
    private var isInForeground = false

    /**
     * Check whether the user has a history of entering the main unit.
     * */
    private var hasEnterMainUnit = false

    /**
     * Check whether the user entered the App through SplashActivity.
     * */
    private var hasEnterBySplashActivity = false

    /**
     * Check whether the user entered the App through DeeplinkActivity.
     * */
    private var hasEnterByDeeplinkActivity = false

    /* ======================== Logical ======================== */

    /**
     * Initializing observer
     * */
    fun init() {
        RelicApplication.relicApplication.registerActivityLifecycleCallbacks(this)
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    /**
     * Check whether the current App is running in the foreground.
     *
     * @return Current state of the app.
     * */
    fun isInForeground(): Boolean {
        return isInForeground
    }

    /**
     * Operation when run on the `foreground`.
     *
     * @param foregroundAction      Foreground operation
     * */
    inline fun runOnForeground(foregroundAction: () -> Unit) {
        if (isInForeground()) {
            foregroundAction.invoke()
        }
    }

    /**
     * Operation when run on the `background`.
     *
     * @param foregroundAction      Foreground operation
     * @param backgroundAction      Background operation
     */
    inline fun runOnForeground(
        foregroundAction: () -> Unit,
        backgroundAction: (() -> Unit)
    ) {
        if (isInForeground()) {
            foregroundAction.invoke()
        } else {
            backgroundAction.invoke()
        }
    }

    /* ======================== override: Activity lifecycle callback ======================== */

    /**
     * Notifies that `ON_START` event occurred.
     *
     *
     * This method will be called after the [LifecycleOwner]'s `onStart` method returns.
     *
     * @param owner the component, whose state was changed
     */
    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        LogUtil.w(TAG, "[$TAG] Enter Foreground.")
        isInForeground = true
    }

    /**
     * Notifies that `ON_STOP` event occurred.
     *
     *
     * This method will be called before the [LifecycleOwner]'s `onStop` method
     * is called.
     *
     * @param owner the component, whose state was changed
     */
    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        LogUtil.w(TAG, "[$TAG] Enter Background.")
        isInForeground = false

        // Only display the splash-ad when user has entered the main unit.
        if (hasEnterMainUnit) {
            isFirstColdStart = dataStoreAppFirstStart
        }
    }

    /* ======================== override: Activity lifecycle callback ======================== */

    /**
     * Called when the Activity calls [super.onCreate()][Activity.onCreate].
     */
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        //
    }

    /**
     * Called when the Activity calls [super.onStart()][Activity.onStart].
     */
    override fun onActivityStarted(activity: Activity) {
        LogUtil.d(TAG, "[${activity::class.java.simpleName}] || onActivityStarted")
        when (activity) {
            is DeepLinkActivity -> hasEnterByDeeplinkActivity = true
            is SplashActivity -> hasEnterBySplashActivity = true
        }
    }

    /**
     * Called when the Activity calls [super.onResume()][Activity.onResume].
     */
    override fun onActivityResumed(activity: Activity) {
        LogUtil.d(TAG, "[${activity::class.java.simpleName}] || onActivityResumed")

        // If you have not entered the main unit and the current Activity is MainActivity,
        // it is marked as having entered the home unit.
        if (!hasEnterMainUnit && (activity is MainActivity)) {
            hasEnterMainUnit = true
            writeSyncData(KEY_IS_FIRST_COLD_START, false)
        }

        // After entering the home page, the subsequent return to
        // the foreground is considered as a hot start.
        if (hasEnterMainUnit) {
            isHotStart = true
        }
    }

    /**
     * Called when the Activity calls [super.onPause()][Activity.onPause].
     */
    override fun onActivityPaused(activity: Activity) {
        //
    }

    /**
     * Called when the Activity calls [super.onStop()][Activity.onStop].
     */
    override fun onActivityStopped(activity: Activity) {
        //
    }

    /**
     * Called when the Activity calls
     * [super.onSaveInstanceState()][Activity.onSaveInstanceState].
     */
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        //
    }

    /**
     * Called when the Activity calls [super.onDestroy()][Activity.onDestroy].
     */
    override fun onActivityDestroyed(activity: Activity) {
        LogUtil.d(TAG, "[${activity::class.java.simpleName}] || onActivityDestroyed")
    }

}