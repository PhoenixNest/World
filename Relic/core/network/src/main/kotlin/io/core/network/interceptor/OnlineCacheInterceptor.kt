package io.core.network.interceptor

import io.common.util.LogUtil
import okhttp3.Interceptor
import okhttp3.Response

open class OnlineCacheInterceptor internal constructor(builder: Builder) : Interceptor {

    private var maxCacheTimeDuration: Int = builder.maxCacheTimeDuration

    companion object {
        private const val TAG = "OnlineCacheInterceptor"

        private const val HEADER_PRAGMA = "Pragma"
        private const val HEADER_CACHE_CONTROL = "Cache-Control"
        private const val DEFAULT_MAX_ONLINE_CACHE_TIME_DURATION = 30 * 60
    }

    /* ======================== constructor ======================== */

    class Builder() {

        internal var maxCacheTimeDuration = DEFAULT_MAX_ONLINE_CACHE_TIME_DURATION

        internal constructor(onlineCacheInterceptor: OnlineCacheInterceptor) : this() {
            this.maxCacheTimeDuration = onlineCacheInterceptor.maxCacheTimeDuration
        }

        fun setMaxOnlineCacheTimeDuration(second: Int): Builder {
            return apply {
                this.maxCacheTimeDuration = second
            }
        }

        fun build(): OnlineCacheInterceptor {
            return OnlineCacheInterceptor(this)
        }

    }

    /* ======================== override ======================== */

    override fun intercept(chain: Interceptor.Chain): Response {
        LogUtil.w(TAG, "Checking With [$TAG]")

        val request = chain.request()
        val response = chain.proceed(request)

        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, "public, max-age=$maxCacheTimeDuration")
            .build()
    }

    /* ======================== Logical ======================== */

    fun maxCacheTimeDuration(): Int {
        return maxCacheTimeDuration
    }

    open fun newBuilder(): Builder {
        return Builder(this)
    }
}