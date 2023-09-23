package io.dev.relic.feature.pages.home.viewmodel.state

import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel

sealed class HomeFoodRecipesDataState {

    /* Common */

    data object Init : HomeFoodRecipesDataState()

    data object Empty : HomeFoodRecipesDataState()

    data object NoFoodRecipesData : HomeFoodRecipesDataState()

    /* Loading */

    data object Fetching : HomeFoodRecipesDataState()

    /* Succeed */

    data class FetchSucceed(
        val modelList: List<FoodRecipesComplexSearchInfoModel?>?
    ) : HomeFoodRecipesDataState()

    /* Failed */

    data class FetchFailed(
        val errorCode: Int?,
        val errorMessage: String?
    ) : HomeFoodRecipesDataState()

}