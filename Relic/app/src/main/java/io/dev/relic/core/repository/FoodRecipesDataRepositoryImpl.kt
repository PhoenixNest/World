package io.dev.relic.core.repository

import io.dev.relic.core.data.database.repository.RelicDatabaseRepository
import io.dev.relic.core.data.network.api.IFoodRecipesApi
import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.core.data.network.api.dto.food_recipes.random_search.FoodRecipesRandomSearchDTO
import io.dev.relic.core.data.network.mappers.FoodRecipesDataMapper.toComplexSearchEntity
import io.dev.relic.domain.model.NetworkResult
import io.dev.relic.domain.repository.IFoodRecipesDataRepository
import javax.inject.Inject

class FoodRecipesDataRepositoryImpl @Inject constructor(
    private val foodRecipesApi: IFoodRecipesApi,
    private val databaseRepository: RelicDatabaseRepository
) : IFoodRecipesDataRepository {

    companion object {
        private const val TAG = "FoodRecipesDataRepository"
    }

    /**
     * Fetch recipes with complex search.
     *
     * @param offset    Paging index number
     * */
    override suspend fun getComplexSearchRecipesData(
        offset: Int
    ): NetworkResult<FoodRecipesComplexSearchDTO> {
        return try {
            val foodRecipesComplexSearchDTO: FoodRecipesComplexSearchDTO = foodRecipesApi.complexSearchData(offset)

            // Always save the latest recipes data to the database.
            databaseRepository.insertComplexSearchRecipesData(foodRecipesComplexSearchDTO.toComplexSearchEntity())

            NetworkResult.Success(data = foodRecipesComplexSearchDTO)
        } catch (exception: Exception) {
            NetworkResult.Failed(message = null)
        }
    }

    /**
     * Fetch random recipes.
     *
     * @param number    Max number of random result
     * */
    override suspend fun getRandomSearchRecipesData(
        number: Int
    ): NetworkResult<FoodRecipesRandomSearchDTO> {
        TODO("Not yet implemented")
    }


}