package io.domain.use_case.food_receipes

import io.domain.use_case.food_receipes.action.complex_search.CacheComplexSearchData
import io.domain.use_case.food_receipes.action.complex_search.GetComplexRecipesData
import io.domain.use_case.food_receipes.action.complex_search.QueryCachedComplexRecipesData

internal const val TAG = "FoodRecipesUseCase"

data class FoodRecipesUseCase(
    val getComplexRecipesData: GetComplexRecipesData,
    val cacheComplexSearchData: CacheComplexSearchData,
    val queryCachedComplexRecipesData: QueryCachedComplexRecipesData
)