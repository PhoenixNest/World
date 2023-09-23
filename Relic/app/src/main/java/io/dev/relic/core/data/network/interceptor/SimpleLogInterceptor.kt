package io.dev.relic.core.data.network.interceptor

import io.dev.relic.global.utils.LogUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class SimpleLogInterceptor : Interceptor {

    companion object {
        private const val TAG: String = "SimpleLogInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val headerMessage: String = if (request.headers.size > 0) request.headers.toString() else "null"
        LogUtil.apply {
            debug(TAG, "┌────── Request ────────────────────────────────────────────────────────────────────────")
            debug(TAG, "| url: ${request.url.toUrl()}")
            debug(TAG, "| method: ${request.method}")
            debug(TAG, "| headers: $headerMessage")
            debug(TAG, "| body: ${request.body}")
            debug(TAG, "└───────────────────────────────────────────────────────────────────────────────────────")
        }

        val response: Response = chain.proceed(request)

        LogUtil.apply {
            debug(TAG, "┌────── Response ────────────────────────────────────────────────────────────────────────")
            debug(TAG, "| body: ${response.body?.source()}")
            debug(TAG, "└───────────────────────────────────────────────────────────────────────────────────────")
        }

        return response
    }
}