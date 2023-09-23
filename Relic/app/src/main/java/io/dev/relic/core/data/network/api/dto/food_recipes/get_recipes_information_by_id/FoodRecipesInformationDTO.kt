package io.dev.relic.core.data.network.api.dto.food_recipes.get_recipes_information_by_id

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.dev.relic.core.data.network.api.dto.food_recipes.Measures
import io.dev.relic.core.data.network.api.dto.food_recipes.NutrientItem

@JsonClass(generateAdapter = true)
data class FoodRecipesInformationDTO(
    @Json(name = "vegetarian")
    val vegetarian: Boolean?,
    @Json(name = "vegan")
    val vegan: Boolean?,
    @Json(name = "glutenFree")
    val glutenFree: Boolean?,
    @Json(name = "dairyFree")
    val dairyFree: Boolean?,
    @Json(name = "veryHealthy")
    val veryHealthy: Boolean?,
    @Json(name = "cheap")
    val cheap: Boolean?,
    @Json(name = "veryPopular")
    val veryPopular: Boolean?,
    @Json(name = "sustainable")
    val sustainable: Boolean?,
    @Json(name = "lowFodmap")
    val lowFodmap: Boolean?,
    @Json(name = "weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int?,
    @Json(name = "gaps")
    val gaps: String?,
    @Json(name = "preparationMinutes")
    val preparationMinutes: Int?,
    @Json(name = "cookingMinutes")
    val cookingMinutes: Int?,
    @Json(name = "aggregateLikes")
    val aggregateLikes: Int?,
    @Json(name = "healthScore")
    val healthScore: Int?,
    @Json(name = "creditsText")
    val creditsText: String?,
    @Json(name = "license")
    val license: String?,
    @Json(name = "sourceName")
    val sourceName: String?,
    @Json(name = "pricePerServing")
    val pricePerServing: Double?,
    @Json(name = "extendedIngredients")
    val extendedIngredients: List<ExtendedIngredientItem?>?,
    @Json(name = "id")
    val id: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "readyInMinutes")
    val readyInMinutes: Int?,
    @Json(name = "servings")
    val servings: Int?,
    @Json(name = "recipes")
    val sourceUrl: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "imageType")
    val imageType: String?,
    @Json(name = "nutrition")
    val nutrition: Nutrition?,
    @Json(name = "summary")
    val summary: String?,
    @Json(name = "cuisines")
    val cuisines: List<Any?>?,
    @Json(name = "dishTypes")
    val dishTypes: List<String?>?,
    @Json(name = "diets")
    val diets: List<Any?>?,
    @Json(name = "occasions")
    val occasions: List<Any?>?,
    @Json(name = "winePairing")
    val winePairing: WinePairing?,
    @Json(name = "instructions")
    val instructions: String?,
    @Json(name = "analyzedInstructions")
    val analyzedInstructions: List<Any?>?,
    @Json(name = "originalId")
    val originalId: Any?,
    @Json(name = "spoonacularSourceUrl")
    val spoonacularSourceUrl: String?
)

@JsonClass(generateAdapter = true)
data class ExtendedIngredientItem(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "aisle")
    val aisle: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "consistency")
    val consistency: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "nameClean")
    val nameClean: String?,
    @Json(name = "original")
    val original: String?,
    @Json(name = "originalName")
    val originalName: String?,
    val amount: Double?,
    @Json(name = "unit")
    val unit: String?,
    @Json(name = "meta")
    val meta: List<String?>?,
    @Json(name = "measures")
    val measures: Measures?
)

@JsonClass(generateAdapter = true)
data class Nutrition(
    @Json(name = "nutrients")
    val nutrients: List<NutrientItem?>?,
    @Json(name = "properties")
    val properties: List<PropertyItem?>?,
    @Json(name = "flavonoids")
    val flavonoids: List<FlavonoidItem?>?,
    @Json(name = "ingredients")
    val ingredients: List<IngredientItem?>?,
    @Json(name = "caloricBreakdown")
    val caloricBreakdown: CaloricBreakdown?,
    @Json(name = "weightPerServing")
    val weightPerServing: WeightPerServing?
)

@JsonClass(generateAdapter = true)
data class PropertyItem(
    @Json(name = "name")
    val name: String?,
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "unit")
    val unit: String?
)

@JsonClass(generateAdapter = true)
data class FlavonoidItem(
    @Json(name = "name")
    val name: String?,
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "unit")
    val unit: String?
)

@JsonClass(generateAdapter = true)
data class IngredientItem(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "unit")
    val unit: String?,
    @Json(name = "nutrients")
    val nutrients: List<NutrientItem?>?
)

@JsonClass(generateAdapter = true)
data class CaloricBreakdown(
    @Json(name = "percentProtein")
    val percentProtein: Double?,
    @Json(name = "percentFat")
    val percentFat: Double?,
    @Json(name = "percentCarbs")
    val percentCarbs: Double?
)

@JsonClass(generateAdapter = true)
data class WeightPerServing(
    @Json(name = "amount")
    val amount: Int?,
    @Json(name = "unit")
    val unit: String?
)

@JsonClass(generateAdapter = true)
data class WinePairing(
    @Json(name = "pairedWines")
    val pairedWines: List<Any?>?,
    @Json(name = "pairingText")
    val pairingText: String?,
    @Json(name = "productMatches")
    val productMatches: List<Any?>?
)