package io.core.network.api

import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.dto.food_recipes.get_recipes_information_by_id.FoodRecipesInformationDTO
import io.data.dto.food_recipes.random_search.FoodRecipesRandomSearchDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * [Spoonacular API](https://spoonacular.com/food-api)
 * */
interface IFoodRecipesApi {

    /**
     * [Get Recipe Information](https://spoonacular.com/food-api/docs#Get-Recipe-Information)
     *
     * Use a recipe id to get full information about a recipe,
     * such as ingredients, nutrition, diet and allergen information, etc.
     *
     * @param apiKey                Your dev api key.
     * @param id                    The id of the recipe.
     * @param includeNutrition      Include nutrition data in the recipe information. Nutrition data is per serving. If you want the nutrition data for the entire recipe, just multiply by the number of servings.
     *
     * @see FoodRecipesInformationDTO
     * */
    @GET("{id}/information?")
    suspend fun getRecipesInformationById(
        @Query("apiKey") apiKey: String,
        @Path("id") id: Int,
        @Query("includeNutrition") includeNutrition: Boolean
    ): FoodRecipesInformationDTO

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
    @GET("complexSearch?")
    suspend fun complexSearchData(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("addRecipeInformation") addRecipeInformation: Boolean,
        @Query("addRecipeNutrition") addRecipeNutrition: Boolean,
        @Query("offset") offset: Int
    ): FoodRecipesComplexSearchDTO

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
    @GET("random?")
    suspend fun randomSearchData(
        @Query("apiKey") apiKey: String,
        @Query("limitLicense") limitLicense: Boolean,
        @Query("tags") tags: String,
        @Query("number") number: Int
    ): FoodRecipesRandomSearchDTO

}