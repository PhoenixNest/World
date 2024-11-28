package io.domain.use_case.food_receipes.action.random

import io.core.network.NetworkParameters.Keys.FOOD_RECIPES_API_DEV_KEY
import io.data.dto.food_recipes.random_search.FoodRecipesRandomDTO
import io.data.model.NetworkResult
import io.domain.repository.IFoodRecipesDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRandomRecipesData @Inject constructor(
    private val foodRecipesDataRepository: IFoodRecipesDataRepository
) {

    /**
     * [Get Random Recipes](https://spoonacular.com/food-api/docs#Get-Random-Recipes)
     *
     * Find random (popular) recipes. `If you need to filter recipes by diet, nutrition etc.`
     * you might want to consider using the complex recipe search endpoint
     * and set the `sort` request parameter to `random`.
     *
     * @param apiKey            Your dev api key.
     * @param includeNutrition  Whether to include nutritional information to returned recipes.
     * @param includeTags       The tags (can be diets, meal types, cuisines, or intolerances) that the recipe must have.
     * @param excludeTags       The tags (can be diets, meal types, cuisines, or intolerances) that the recipe must NOT have.
     * @param number            The number of random recipes to be returned (between 1 and 100).
     *
     * @see FoodRecipesRandomDTO
     * */
    operator fun invoke(
        apiKey: String = FOOD_RECIPES_API_DEV_KEY,
        includeNutrition: Boolean,
        includeTags: String,
        excludeTags: String,
        number: Int
    ): Flow<NetworkResult<FoodRecipesRandomDTO>> {
        return flow {
            // Fetch the latest data from remote-server.
            val result = foodRecipesDataRepository.getRandomRecipesData(
                apiKey = apiKey,
                includeNutrition = includeNutrition,
                includeTags = includeTags,
                excludeTags = excludeTags,
                number = number
            )
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}