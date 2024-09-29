package io.common.system

import android.content.Context
import android.os.Process
import android.os.health.HealthStats
import io.common.RelicSystemServiceManager.getSystemHealthManager
import io.common.util.LogUtil

object HealthUtil {

    private const val TAG = "HealthUtil"

    private var healthStats: HealthStats? = null

    /**
     * [HealthStats](https://developer.android.com/reference/android/os/health/HealthStats)
     *
     * A HealthStats object contains system health data about an application.
     * */
    fun getSystemHealthStats(
        context: Context,
        uid: Int = Process.myUid()
    ): HealthStats? {
        if (healthStats != null) {
            return healthStats
        }

        return try {
            getSystemHealthManager(context)?.takeUidSnapshot(uid)
        } catch (exception: Exception) {
            LogUtil.e(TAG, "[System Health] Error, ${exception.message}")
            exception.printStackTrace()
            null
        }
    }

}