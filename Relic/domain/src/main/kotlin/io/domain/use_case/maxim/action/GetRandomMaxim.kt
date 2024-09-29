package io.domain.use_case.maxim.action

import io.core.network.NetworkParameters.Keys.HITOKOTO_API_DEV_KEY
import io.data.dto.maxim.MaximDTO
import io.data.model.NetworkResult
import io.domain.repository.IMaximDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRandomMaxim @Inject constructor(
    private val maximRepository: IMaximDataRepository
) {

    operator fun invoke(
        token: String = HITOKOTO_API_DEV_KEY,
        category: String = "",
        charSet: String = "Utf-8"
    ): Flow<NetworkResult<MaximDTO>> {
        return flow {
            // Fetch the latest data from remote-server.
            val result = maximRepository.getRandomMaxim(
                token = token,
                category = category,
                charSet = charSet
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}