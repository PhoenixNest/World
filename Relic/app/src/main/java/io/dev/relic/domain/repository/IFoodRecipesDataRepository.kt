package io.dev.relic.domain.repository

import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.core.data.network.api.dto.food_recipes.random_search.FoodRecipesRandomSearchDTO
import io.dev.relic.domain.model.NetworkResult

/**
 * @see io.dev.relic.core.repository.FoodRecipesDataRepositoryImpl
 * */
interface IFoodRecipesDataRepository {

    /**
     * Fetch recipes with complex search.
     *
     * @param offset    Paging index number
     * */
    fun getComplexSearchRecipesData(
        offset: Int
    ): NetworkResult<FoodRecipesComplexSearchDTO>

    /**
     * Fetch random recipes.
     *
     * @param number    Max number of random result
     * */
    fun getRandomSearchRecipesData(
        number: Int
    ): NetworkResult<FoodRecipesRandomSearchDTO>

}