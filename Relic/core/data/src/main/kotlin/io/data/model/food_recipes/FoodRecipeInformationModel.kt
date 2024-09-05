package io.data.model.food_recipes

import io.data.dto.food_recipes.get_recipes_information_by_id.ExtendedIngredientItem
import io.data.dto.food_recipes.get_recipes_information_by_id.Nutrition
import io.data.dto.food_recipes.get_recipes_information_by_id.WinePairing

data class FoodRecipeInformationModel(
    val id: Int?,
    val title: String?,
    val vegetarian: Boolean?,
    val vegan: Boolean?,
    val glutenFree: Boolean?,
    val dairyFree: Boolean?,
    val veryHealthy: Boolean?,
    val cheap: Boolean?,
    val veryPopular: Boolean?,
    val sustainable: Boolean?,
    val lowFodmap: Boolean?,
    val weightWatcherSmartPoints: Int?,
    val gaps: String?,
    val preparationMinutes: Int?,
    val cookingMinutes: Int?,
    val aggregateLikes: Int?,
    val healthScore: Int?,
    val creditsText: String?,
    val license: String?,
    val sourceName: String?,
    val pricePerServing: Double?,
    val extendedIngredients: List<ExtendedIngredientItem?>?,
    val readyInMinutes: Int?,
    val servings: Int?,
    val sourceUrl: String?,
    val image: String?,
    val imageType: String?,
    val nutrition: Nutrition?,
    val summary: String?,
    val cuisines: List<Any?>?,
    val dishTypes: List<String?>?,
    val diets: List<Any?>?,
    val occasions: List<Any?>?,
    val winePairing: WinePairing?,
    val instructions: String?,
    val analyzedInstructions: List<Any?>?,
)