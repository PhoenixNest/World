package io.dev.relic.domain.use_case.food_receipes

import io.dev.relic.domain.use_case.food_receipes.action.complex_search.CacheComplexSearchData
import io.dev.relic.domain.use_case.food_receipes.action.complex_search.FetchComplexRecipesData
import io.dev.relic.domain.use_case.food_receipes.action.complex_search.ReadCacheComplexRecipesData

internal const val TAG = "FoodRecipesUseCase"

data class FoodRecipesUseCase(
    val fetchComplexRecipesData: FetchComplexRecipesData,
    val cacheComplexSearchData: CacheComplexSearchData,
    val readCacheComplexRecipesData: ReadCacheComplexRecipesData
)