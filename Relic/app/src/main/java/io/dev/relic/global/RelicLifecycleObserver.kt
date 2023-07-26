package io.dev.relic.global

import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import io.dev.relic.feature.activities.main.MainActivity
import io.dev.relic.feature.activities.splash.SplashActivity
import io.dev.relic.global.utils.LogUtil

/**
 * Reference link: [App startup time](https://developer.android.com/topic/performance/vitals/launch-time)
 * */
object RelicLifecycleObserver : DefaultLifecycleObserver, ActivityLifecycleCallbacks {

    private const val TAG: String = "RelicLifecycleObserver"

    /**
     * [Cold start](https://developer.android.com/topic/performance/vitals/launch-time#cold)
     * */
    private var isFirstColdStart: Boolean = false

    /**
     * [Warm start](https://developer.android.com/topic/performance/vitals/launch-time#warm)
     * */
    private var isWarmStart: Boolean = false

    /**
     * [Hot start](https://developer.android.com/topic/performance/vitals/launch-time#hot)
     * */
    private var isHotStart: Boolean = false

    /**
     * Check whether the current App is running in the foreground.
     * */
    private var isInForeground: Boolean = false

    /**
     * Check whether the user has a history of entering the main unit.
     * */
    private var hasEnterMainUnit: Boolean = false

    /**
     * Check whether the user entered the App through SplashActivity.
     * */
    private var hasEnterBySplashActivity: Boolean = false

    /**
     * Only show splash-ad when the app has re-entered foreground.
     * */
    private var shouldShowSplashAd: Boolean = false

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

    /**
     * Trying to show the splash-ad when hot start.
     *
     * @param activity      Host of the AdActivity.
     * */
    private fun tryShowHotSplashAd(activity: Activity) {
        // TODO
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
        LogUtil.warning(TAG, "[Relic] Enter Foreground.")
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
        LogUtil.warning(TAG, "[Relic] Enter Background.")
        isInForeground = false

        // Only display the splash-ad when user has entered the main unit.
        if (hasEnterMainUnit) {
            shouldShowSplashAd = true
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
        LogUtil.debug(TAG, "[${activity::class.java.simpleName}] || onActivityStarted")

        // Update parameter
        hasEnterBySplashActivity = when {

            // Because the mark: isHotStart will only be updated when entered main unit,
            // so the user may stay in:
            // - SplashActivity
            // - AdActivity
            !isHotStart -> true

            // User has stay in SplashActivity, skip
            activity is SplashActivity -> true

            // User has stay in AdActivity, skip
            // activity is AdActivity -> true

            // Otherwise, try to show the hot start splash-ad
            else -> false
        }
    }

    /**
     * Called when the Activity calls [super.onResume()][Activity.onResume].
     */
    override fun onActivityResumed(activity: Activity) {
        LogUtil.debug(TAG, "[${activity::class.java.simpleName}] || onActivityResumed")

        // If you have not entered the main unit and the current Activity is MainActivity,
        // it is marked as having entered the home unit.
        if (!hasEnterMainUnit && (activity is MainActivity)) {
            hasEnterMainUnit = true
        }

        // After entering the home page, the subsequent return to
        // the foreground is considered as a hot start.
        if (hasEnterMainUnit) {
            isHotStart = true
        }

        // Whether hot start splash-ad need to be displayed.
        if (shouldShowSplashAd) {
            shouldShowSplashAd = false
            tryShowHotSplashAd(activity = activity)
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
        LogUtil.debug(TAG, "[${activity::class.java.simpleName}] || onActivityDestroyed")
    }

}