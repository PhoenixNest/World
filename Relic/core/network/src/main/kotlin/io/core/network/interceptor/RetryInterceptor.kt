package io.core.network.interceptor

import io.common.util.LogUtil
import okhttp3.Interceptor
import okhttp3.Response

open class RetryInterceptor internal constructor(builder: Builder) : Interceptor {

    private var maxRetryTimes = builder.maxRetryTimes

    private var currentRetryTimes = 0

    companion object {
        private const val TAG = "RetryInterceptor"

        /**
         * Because the retryOnConnectionFailure has enabled,
         * the final retry times will be:
         *
         * auto-retry + manual-retry = 1 + 2 = 3 times etc.
         * */
        private const val DEFAULT_MAX_RETRY_TIMES = 2
    }

    /* ======================== constructor ======================== */

    constructor() : this(Builder())

    class Builder() {

        internal var maxRetryTimes = DEFAULT_MAX_RETRY_TIMES

        internal constructor(retryInterceptor: RetryInterceptor) : this() {
            this.maxRetryTimes = retryInterceptor.maxRetryTimes
        }

        fun setMaxRetryTimes(times: Int): Builder {
            return apply {

                require(times >= DEFAULT_MAX_RETRY_TIMES) {
                    "The max amount of retry-times must be >= DEFAULT_MAX_RETRY_TIMES (the default value is 2)"
                }

                this.maxRetryTimes = times
            }
        }

        fun build(): RetryInterceptor {
            return RetryInterceptor(this)
        }
    }

    /* ======================== override ======================== */

    override fun intercept(chain: Interceptor.Chain): Response {
        LogUtil.w(TAG, "Checking With [$TAG]")

        val request = chain.request()
        var response = chain.proceed(request)

        while (!response.isSuccessful && currentRetryTimes < maxRetryTimes) {
            currentRetryTimes++
            LogUtil.d(TAG, "currentRetryTimes: $currentRetryTimes")
            response.close()
            response = chain.call().clone().execute()
        }

        return response
    }

    /* ======================== Logical ======================== */

    fun maxRetryTimes(): Int {
        return maxRetryTimes
    }

    open fun newBuilder(): Builder {
        return Builder(this)
    }
}