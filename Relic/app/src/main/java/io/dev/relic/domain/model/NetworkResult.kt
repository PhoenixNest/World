package io.dev.relic.domain.model

sealed class NetworkResult<T>(
    val code: String? = null,
    val message: String? = null,
    val data: T? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data = data)

    class Failed<T>(message: String?, data: T? = null) : NetworkResult<T>(message = message, data = data)
}
