package io.dev.relic.feature.function.food_recipes

import io.data.model.food_recipes.FoodRecipesComplexSearchModel

sealed class FoodRecipesDataState {

    /* Common */

    data object Init : FoodRecipesDataState()

    data object Empty : FoodRecipesDataState()

    data object NoFoodRecipesData : FoodRecipesDataState()

    /* Loading */

    data object Fetching : FoodRecipesDataState()

    /* Succeed */

    data class FetchSucceed(
        val modelList: List<FoodRecipesComplexSearchModel?>
    ) : FoodRecipesDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : FoodRecipesDataState()

}