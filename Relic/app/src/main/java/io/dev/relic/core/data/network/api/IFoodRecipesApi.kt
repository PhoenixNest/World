package io.dev.relic.core.data.network.api

import io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
import io.dev.relic.core.data.network.api.dto.food_recipes.get_recipes_information_by_id.FoodRecipesInformationDTO
import io.dev.relic.core.data.network.api.dto.food_recipes.random_search.FoodRecipesRandomSearchDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * [Spoonacular API](https://spoonacular.com/food-api)
 * */
interface IFoodRecipesApi {

    @GET("recipes/{id}/information")
    suspend fun getRecipesInformationById(
        @Path("id") recipesId: Int
    ): FoodRecipesInformationDTO

    @GET("complexSearch")
    suspend fun complexSearchData(
        @Query("offset") offset: Int
    ): FoodRecipesComplexSearchDTO

    @GET("recipes/random")
    suspend fun randomSearchData(
        //
    ): FoodRecipesRandomSearchDTO

}