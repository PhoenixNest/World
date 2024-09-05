package io.domain.use_case.food_receipes.action.information

import io.core.network.NetworkParameters.Keys.FOOD_RECIPES_API_DEV_KEY
import io.data.dto.food_recipes.get_recipes_information_by_id.FoodRecipesInformationDTO
import io.data.model.NetworkResult
import io.domain.repository.IFoodRecipesDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetFoodRecipeInformationById @Inject constructor(
    private val foodRecipesDataRepository: IFoodRecipesDataRepository
) {

    operator fun invoke(
        apiKey: String = FOOD_RECIPES_API_DEV_KEY,
        recipeId: Int,
        includeNutrition: Boolean
    ): Flow<NetworkResult<FoodRecipesInformationDTO>> {
        return flow {
            val result = foodRecipesDataRepository.getRecipesInformationById(
                apiKey = apiKey,
                id = recipeId,
                includeNutrition = includeNutrition
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}