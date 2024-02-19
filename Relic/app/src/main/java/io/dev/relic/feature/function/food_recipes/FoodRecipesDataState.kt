package io.dev.relic.feature.function.food_recipes

sealed class FoodRecipesDataState {

    /* Common */

    data object Init : FoodRecipesDataState()

    data object Empty : FoodRecipesDataState()

    data object NoFoodRecipesData : FoodRecipesDataState()

    /* Loading */

    data object Fetching : FoodRecipesDataState()

    /* Succeed */

    data class FetchSucceed<T>(
        val data: T?
    ) : FoodRecipesDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : FoodRecipesDataState()

}