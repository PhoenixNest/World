package io.dev.relic.core.module.data.network.interceptor

import io.dev.relic.global.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

open class RetryInterceptor internal constructor(builder: Builder) : Interceptor {

    private var maxRetryTimes: Int = builder.maxRetryTimes

    private var currentRetryTimes = 0

    companion object {
        private const val TAG: String = "RetryInterceptor"

        /**
         * Because the retryOnConnectionFailure has enabled,
         * the final retry times will be:
         *
         * auto-retry + manual-retry = 1 + 2 = 3 times etc.
         * */
        private const val DEFAULT_MAX_RETRY_TIMES: Int = 2
    }

    /* ======================== constructor ======================== */

    constructor() : this(Builder())

    class Builder() {

        internal var maxRetryTimes: Int = DEFAULT_MAX_RETRY_TIMES

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
        LogUtil.warning(TAG, "Checking With [${TAG}]")

        val request: Request = chain.request()
        var response: Response = chain.proceed(request)

        while (!response.isSuccessful && currentRetryTimes < maxRetryTimes) {
            currentRetryTimes++
            LogUtil.debug(TAG, "currentRetryTimes: $currentRetryTimes")
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