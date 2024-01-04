package io.domain.use_case.food_receipes.action.complex_search

import io.core.network.NetworkParameters
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.model.NetworkResult
import io.domain.repository.IFoodRecipesDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchComplexRecipesData @Inject constructor(
    private val foodRecipesDataRepository: IFoodRecipesDataRepository
) {

    /**
     * [Search Recipes](https://spoonacular.com/food-api/docs#Search-Recipes-Complex)
     *
     * Search through thousands of recipes using advanced filtering and ranking.
     *
     * `NOTE`: This method combines searching by query, by ingredients, and by nutrients into one endpoint.
     *
     * @param apiKey                     Your dev api key.
     * @param query                      The (natural language) recipe search query.
     * @param addRecipeInformation       If set to true, you get more information about the recipes returned.
     * @param addRecipeNutrition         If set to true, you get nutritional information about each recipes returned.
     * @param offset                     The number of results to skip (between 0 and 900).
     *
     * @see FoodRecipesComplexSearchDTO
     * */
    operator fun invoke(
        apiKey: String = NetworkParameters.Keys.FOOD_RECIPES_API_DEV_KEY,
        query: String,
        addRecipeInformation: Boolean,
        addRecipeNutrition: Boolean,
        offset: Int
    ): Flow<NetworkResult<FoodRecipesComplexSearchDTO>> {
        return flow {
            // Fetch the latest data from remote-server.
            val result = foodRecipesDataRepository.getComplexSearchRecipesData(
                apiKey = apiKey,
                query = query,
                addRecipeInformation = addRecipeInformation,
                addRecipeNutrition = addRecipeNutrition,
                offset = offset
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}
