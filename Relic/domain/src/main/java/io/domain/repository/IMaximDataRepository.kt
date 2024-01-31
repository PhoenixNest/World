package io.domain.repository

import io.data.dto.maxim.MaximDTO
import io.data.model.NetworkResult

/**
 * @see io.domain.repository.impl.MaximDataRepositoryImpl
 * */
interface IMaximDataRepository {

    suspend fun getRandomMaxim(
        token: String,
        category: String,
        charSet: String
    ): NetworkResult<MaximDTO>

}