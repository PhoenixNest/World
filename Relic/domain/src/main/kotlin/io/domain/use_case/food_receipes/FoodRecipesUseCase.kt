package io.domain.use_case.food_receipes

import io.domain.use_case.food_receipes.action.complex_search.CacheComplexSearchData
import io.domain.use_case.food_receipes.action.complex_search.GetComplexSearchData
import io.domain.use_case.food_receipes.action.complex_search.QueryCachedComplexRecipesData
import io.domain.use_case.food_receipes.action.complex_search.RemoveComplexSearchData
import io.domain.use_case.food_receipes.action.information.GetFoodRecipeInformationById
import io.domain.use_case.food_receipes.action.random.GetRandomRecipesData

internal const val TAG = "FoodRecipesUseCase"

data class FoodRecipesUseCase(

    /* ======================== Complex search ======================== */

    val getComplexSearchData: GetComplexSearchData,
    val cacheComplexSearchData: CacheComplexSearchData,
    val deleteAllComplexSearchData: RemoveComplexSearchData,
    val queryCachedComplexRecipesData: QueryCachedComplexRecipesData,

    /* ======================== Random search ======================== */

    val getRandomRecipesData: GetRandomRecipesData,

    /* ======================== Information ======================== */

    val getRecipeInformationById: GetFoodRecipeInformationById
)