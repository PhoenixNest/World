package io.data.model

sealed class NetworkResult<T>(
    val code: Int? = null,
    val message: String? = null,
    val data: T? = null
) {
    class Loading<T> : NetworkResult<T>()
    class Success<T>(data: T) : NetworkResult<T>(data = data)
    class Failed<T>(message: String?, data: T? = null) : NetworkResult<T>(message = message, data = data)
}
