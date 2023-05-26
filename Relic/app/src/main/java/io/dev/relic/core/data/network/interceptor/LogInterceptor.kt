package io.dev.relic.core.data.network.interceptor

import io.dev.relic.global.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LogInterceptor : Interceptor {

    companion object {
        private const val TAG: String = "LogInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()

        LogUtil.apply {
            debug(TAG, "request url: ${request.url.toUrl()}")
            debug(TAG, "request method: ${request.method}")
            debug(TAG, "request headers: ${request.headers}")
            debug(TAG, "request body: ${request.body}")
        }

        return chain.proceed(request)
    }
}