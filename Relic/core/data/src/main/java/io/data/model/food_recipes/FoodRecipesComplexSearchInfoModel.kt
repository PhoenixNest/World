package io.data.model.food_recipes

data class FoodRecipesComplexSearchInfoModel(
    val id: Int?,
    val title: String?,
    val author: String?,
    val image: String?,
    val isVegan: Boolean?,
    val healthScore: Int?,
    val cookTime: Int?
)