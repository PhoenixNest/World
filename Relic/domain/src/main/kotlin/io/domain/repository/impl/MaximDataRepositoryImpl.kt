package io.domain.repository.impl

import io.core.network.api.IMaximApi
import io.data.dto.maxim.MaximDTO
import io.data.model.NetworkResult
import io.domain.repository.IMaximDataRepository
import javax.inject.Inject

/**
 * @see IMaximDataRepository
 * */
class MaximDataRepositoryImpl @Inject constructor(
    private val maximApi: IMaximApi
) : IMaximDataRepository {

    private var maximResult: NetworkResult<MaximDTO> = NetworkResult.Loading()

    companion object {
        private const val TAG = "MaximDataRepository"
    }

    override suspend fun getRandomMaxim(
        token: String,
        category: String,
        charSet: String
    ): NetworkResult<MaximDTO> {
        maximResult = try {
            val data = maximApi.getRandomMaxim(
                token = token,
                category = category,
                charSet = charSet
            )

            NetworkResult.Success(data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }

        return maximResult
    }
}