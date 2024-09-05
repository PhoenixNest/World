package io.domain.use_case.food_receipes

import io.domain.use_case.food_receipes.action.complex_search.CacheComplexSearchData
import io.domain.use_case.food_receipes.action.complex_search.GetComplexRecipesData
import io.domain.use_case.food_receipes.action.complex_search.QueryCachedComplexRecipesData
import io.domain.use_case.food_receipes.action.complex_search.RemoveComplexSearchData
import io.domain.use_case.food_receipes.action.information.GetFoodRecipeInformationById

internal const val TAG = "FoodRecipesUseCase"

data class FoodRecipesUseCase(

    /* ======================== Complex search ======================== */

    val getComplexRecipesData: GetComplexRecipesData,
    val cacheComplexSearchData: CacheComplexSearchData,
    val deleteAllComplexSearchData: RemoveComplexSearchData,
    val queryCachedComplexRecipesData: QueryCachedComplexRecipesData,

    /* ======================== Random search ======================== */

    //

    /* ======================== Information ======================== */

    val getRecipeInformationById: GetFoodRecipeInformationById
)