package io.domain.repository.impl

import io.core.network.api.IFoodRecipesApi
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.dto.food_recipes.get_recipes_information_by_id.FoodRecipesInformationDTO
import io.data.dto.food_recipes.random_search.FoodRecipesRandomSearchDTO
import io.data.model.NetworkResult
import io.domain.repository.IFoodRecipesDataRepository
import javax.inject.Inject

/**
 * @see IFoodRecipesDataRepository
 * */
class FoodRecipesDataRepositoryImpl @Inject constructor(
    private val foodRecipesApi: IFoodRecipesApi
) : IFoodRecipesDataRepository {

    /**
     * Remote server result of complex search.
     *
     * @see IFoodRecipesApi.complexSearchData
     * */
    private var complexSearchResult: NetworkResult<FoodRecipesComplexSearchDTO> = NetworkResult.Loading()

    /**
     * Remote server result of random search.
     *
     * @see IFoodRecipesApi.randomSearchData
     * */
    private var randomSearchResult: NetworkResult<FoodRecipesRandomSearchDTO> = NetworkResult.Loading()

    /**
     * Remote server result of recipe information.
     *
     * @see IFoodRecipesApi.getRecipesInformationById
     * */
    private var recipeInformation: NetworkResult<FoodRecipesInformationDTO> = NetworkResult.Loading()

    companion object {
        private const val TAG = "FoodRecipesDataRepository"
    }

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
    override suspend fun getComplexSearchRecipesData(
        apiKey: String,
        query: String,
        addRecipeInformation: Boolean,
        addRecipeNutrition: Boolean,
        offset: Int
    ): NetworkResult<FoodRecipesComplexSearchDTO> {
        complexSearchResult = try {
            val data = foodRecipesApi.complexSearchData(
                apiKey = apiKey,
                query = query,
                addRecipeInformation = addRecipeInformation,
                addRecipeNutrition = addRecipeNutrition,
                offset = offset
            )

            NetworkResult.Success(data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }
        return complexSearchResult
    }

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
    override suspend fun getRandomSearchRecipesData(
        apiKey: String,
        limitLicense: Boolean,
        tags: String,
        number: Int
    ): NetworkResult<FoodRecipesRandomSearchDTO> {
        randomSearchResult = try {
            val data = foodRecipesApi.randomSearchData(
                apiKey = apiKey,
                limitLicense = limitLicense,
                tags = tags,
                number = number
            )

            NetworkResult.Success(data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }
        return randomSearchResult
    }

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
    override suspend fun getRecipesInformationById(
        apiKey: String,
        id: Int,
        includeNutrition: Boolean
    ): NetworkResult<FoodRecipesInformationDTO> {
        recipeInformation = try {
            val data = foodRecipesApi.getRecipesInformationById(
                apiKey = apiKey,
                id = id,
                includeNutrition = includeNutrition
            )

            NetworkResult.Success(data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }

        return recipeInformation
    }
}