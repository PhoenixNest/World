package io.core.network.interceptor

import io.common.util.LogUtil
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

open class AuthInterceptor internal constructor(builder: Builder) : Interceptor {

    private var authHeaderValue: String = builder.authHeaderValue

    companion object {
        private const val TAG = "AuthInterceptor"
        private const val KEY_HEADER_AUTHORIZATION: String = "Authorization"
    }

    /* ======================== constructor ======================== */

    constructor() : this(Builder())

    class Builder() {

        internal var authHeaderValue: String = "relic-header"

        internal constructor(authInterceptor: AuthInterceptor) : this() {
            this.authHeaderValue = authInterceptor.authHeaderValue
        }

        fun setAuthHeaderValue(str: String): Builder {
            return apply {
                this.authHeaderValue = str
            }
        }

        fun build(): AuthInterceptor {
            return AuthInterceptor(this)
        }

    }

    /* ======================== override ======================== */

    override fun intercept(chain: Interceptor.Chain): Response {
        LogUtil.w(TAG, "Checking With [$TAG]")

        val request: Request = chain.request()

        if (authHeaderValue.isBlank() || authHeaderValue.isEmpty()) {
            return chain.proceed(request)
        }

        val newRequest: Request = request
            .newBuilder()
            .addHeader(KEY_HEADER_AUTHORIZATION, authHeaderValue)
            .build()

        return chain.proceed(newRequest)
    }

    /* ======================== Logical ======================== */

    fun authHeaderValue(): Pair<String, String> {
        return Pair(KEY_HEADER_AUTHORIZATION, authHeaderValue)
    }

    open fun newBuilder(): Builder {
        return Builder(this)
    }
}