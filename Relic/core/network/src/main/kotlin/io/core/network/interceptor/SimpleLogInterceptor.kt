package io.core.network.interceptor

import io.common.util.LogUtil
import okhttp3.Interceptor
import okhttp3.Response

class SimpleLogInterceptor : Interceptor {

    companion object {
        private const val TAG = "SimpleLogInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val headerMessage = if (request.headers.size > 0) request.headers.toString() else "null"
        LogUtil.apply {
            d(TAG, "┌────── Request ────────────────────────────────────────────────────────────────────────")
            d(TAG, "| url: ${request.url.toUrl()}")
            d(TAG, "| method: ${request.method}")
            d(TAG, "| headers: $headerMessage")
            d(TAG, "| body: ${request.body}")
            d(TAG, "└───────────────────────────────────────────────────────────────────────────────────────")
        }

        val response = chain.proceed(request)

        LogUtil.apply {
            d(TAG, "┌────── Response ────────────────────────────────────────────────────────────────────────")
            d(TAG, "| body: ${response.body?.source()}")
            d(TAG, "└───────────────────────────────────────────────────────────────────────────────────────")
        }

        return response
    }
}