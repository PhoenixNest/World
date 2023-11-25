package io.core.network.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.getSystemService
import dagger.hilt.android.qualifiers.ApplicationContext
import io.common.util.LogUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineCacheInterceptor internal constructor(builder: Builder) : Interceptor {

    private var applicationContext: Context? = builder.applicationContext

    private var maxOfflineCacheDuration: Int = builder.maxOfflineCacheDuration

    private var timeUnit: TimeUnit = builder.timeUnit

    companion object {

        private const val TAG: String = "OfflineCacheInterceptor"

        /**
         * Default duration of the offline-cache in app.
         * */
        private const val DEFAULT_DURATION_OF_OFFLINE_CACHE: Int = 24

        /**
         * Default time unit of the offline-cache.
         * */
        private val DEFAULT_TIME_UNIT: TimeUnit = TimeUnit.HOURS

    }

    /* ======================== constructor ======================== */

    constructor() : this(Builder())

    class Builder() {

        internal var applicationContext: Context? = null

        internal var maxOfflineCacheDuration: Int = DEFAULT_DURATION_OF_OFFLINE_CACHE

        internal var timeUnit: TimeUnit = DEFAULT_TIME_UNIT

        internal constructor(offlineCacheInterceptor: OfflineCacheInterceptor) : this() {
            this.applicationContext = offlineCacheInterceptor.applicationContext
            this.maxOfflineCacheDuration = offlineCacheInterceptor.maxOfflineCacheDuration
            this.timeUnit = offlineCacheInterceptor.timeUnit
        }

        fun setApplicationContext(@ApplicationContext context: Context?): Builder {

            require(context != null) {
                "$TAG applicationContext must not be null"
            }

            return apply {
                this.applicationContext = context
            }
        }

        fun setMaxOfflineCacheDuration(duration: Int): Builder {
            return apply {
                this.maxOfflineCacheDuration = duration
            }
        }

        fun setOfflineCacheTimeUnit(timeUnit: TimeUnit): Builder {
            return apply {
                this.timeUnit = timeUnit
            }
        }

        fun build(): OfflineCacheInterceptor {
            return OfflineCacheInterceptor(this)
        }
    }

    /* ======================== override ======================== */

    override fun intercept(chain: Interceptor.Chain): Response {
        LogUtil.warning(TAG, "Checking With [$TAG]")

        val request: Request = chain.request()
        val response: Response = chain.proceed(request)

        // Use Network monitor to check if we should use this interceptor
        if (!isNetworkAvailable()) {
            val cacheControl: CacheControl = CacheControl.Builder()
                .onlyIfCached()
                .maxStale(maxOfflineCacheDuration, timeUnit)
                .build()

            val newRequest: Request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()

            return chain.proceed(newRequest)
        }

        return response
    }

    @Suppress("DEPRECATION")
    private fun isNetworkAvailable(): Boolean {
        var isNetworkAvailable = false
        applicationContext?.let { context: Context ->
            val systemService: ConnectivityManager? = context.getSystemService()
            val networkInfo: NetworkInfo? = systemService?.activeNetworkInfo
            isNetworkAvailable = (networkInfo != null && networkInfo.isConnected)
        }

        return isNetworkAvailable
    }
}