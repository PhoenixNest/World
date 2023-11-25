package io.domain.repository.impl

import io.core.database.repository.RelicDatabaseRepository
import io.core.network.api.IFoodRecipesApi
import io.data.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.data.dto.food_recipes.random_search.FoodRecipesRandomSearchDTO
import io.data.mappers.FoodRecipesDataMapper.toComplexSearchEntity
import io.data.model.NetworkResult
import io.domain.repository.IFoodRecipesDataRepository
import javax.inject.Inject

class FoodRecipesDataRepositoryImpl @Inject constructor(
    private val foodRecipesApi: IFoodRecipesApi,
    private val databaseRepository: RelicDatabaseRepository
) : IFoodRecipesDataRepository {

    private var complexSearchResult: NetworkResult<FoodRecipesComplexSearchDTO> = NetworkResult.Loading()

    private var randomSearchResult: NetworkResult<FoodRecipesRandomSearchDTO> = NetworkResult.Loading()

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
            val data: FoodRecipesComplexSearchDTO = foodRecipesApi.complexSearchData(
                apiKey = apiKey,
                query = query,
                addRecipeInformation = addRecipeInformation,
                addRecipeNutrition = addRecipeNutrition,
                offset = offset
            )

            // Always save the latest recipes data to the database.
            databaseRepository.insertComplexSearchRecipesData(data.toComplexSearchEntity())
            NetworkResult.Success(data = data)
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
            val data: FoodRecipesRandomSearchDTO = foodRecipesApi.randomSearchData(
                apiKey = apiKey,
                limitLicense = limitLicense,
                tags = tags,
                number = number
            )

            // Always save the latest recipes data to the database.
            // databaseRepository.insertRandomSearchRecipesData(data.toRandomSearchEntity())
            NetworkResult.Success(data = data)
        } catch (exception: Exception) {
            exception.printStackTrace()
            NetworkResult.Failed(message = exception.message ?: "Unknown error occurred.")
        }
        return randomSearchResult
    }
}