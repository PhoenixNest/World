package io.dev.relic.domain.model.food_recipes

/**
 * @see io.dev.relic.core.data.network.api.dto.food_recipes.complex_search.FoodRecipesComplexSearchDTO
 * */
data class FoodRecipesComplexSearchInfoModel(
    val id: Int,
    val title: String,
    val image: String,
    val imageType: String
)