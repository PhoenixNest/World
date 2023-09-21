package io.dev.relic.domain.use_case.food_receipes.action.complex_search

import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.repository.IFoodRecipesDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchComplexRecipesData @Inject constructor(
    private val foodRecipesDataRepository: IFoodRecipesDataRepository
) {
    operator fun invoke(offset: Int): Flow<NetworkResult<FoodRecipesComplexSearchDTO>> {
        return flow {
            // Fetch the latest data from remote-server.
            emit(foodRecipesDataRepository.getComplexSearchRecipesData(offset))
        }.flowOn(Dispatchers.IO)
    }
}
