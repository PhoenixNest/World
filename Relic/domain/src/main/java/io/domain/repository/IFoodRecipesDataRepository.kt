package io.domain.repository

import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.dto.food_recipes.random_search.FoodRecipesRandomSearchDTO
import io.data.model.NetworkResult

/**
 * @see io.domain.repository.impl.FoodRecipesDataRepositoryImpl
 * */
interface IFoodRecipesDataRepository {

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
    suspend fun getComplexSearchRecipesData(
        apiKey: String,
        query: String,
        addRecipeInformation: Boolean,
        addRecipeNutrition: Boolean,
        offset: Int
    ): NetworkResult<FoodRecipesComplexSearchDTO>

    /**
     * [Get Random Recipes](https://spoonacular.com/food-api/docs#Get-Random-Recipes)
     *
     * Find random (popular) recipes. `If you need to filter recipes by diet, nutrition etc.`
     * you might want to consider using the complex recipe search endpoint
     * and set the sort request parameter to random.
     *
     * @param apiKey            Your dev api key.
     * @param limitLicense      Whether the recipes should have an open license that allows display with proper attribution.
     * @param tags              The tags (can be diets, meal types, cuisines, or intolerances) that the recipe must have.
     * @param number            The number of random recipes to be returned (between 1 and 100).
     *
     * @see FoodRecipesRandomSearchDTO
     * */
    suspend fun getRandomSearchRecipesData(
        apiKey: String,
        limitLicense: Boolean,
        tags: String,
        number: Int
    ): NetworkResult<FoodRecipesRandomSearchDTO>

}