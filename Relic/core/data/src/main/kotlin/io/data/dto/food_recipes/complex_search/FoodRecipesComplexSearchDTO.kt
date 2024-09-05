package io.data.dto.food_recipes.complex_search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.data.dto.food_recipes.NutrientItem

@JsonClass(generateAdapter = true)
data class FoodRecipesComplexSearchDTO(
    @Json(name = "results")
    val results: List<ResultItem?>?,
    @Json(name = "offset")
    val offset: Int?,
    @Json(name = "number")
    val number: Int?,
    @Json(name = "totalResults")
    val totalResults: Int?
)

@JsonClass(generateAdapter = true)
data class ResultItem(
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
    @Json(name = "id")
    val id: Int?,
    @Json(name = "title")
    val title: String?,
    @Json(name = "readyInMinutes")
    val readyInMinutes: Int?,
    @Json(name = "servings")
    val servings: Int?,
    @Json(name = "sourceUrl")
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
    val cuisines: List<String?>?,
    @Json(name = "dishTypes")
    val dishTypes: List<String?>?,
    @Json(name = "diets")
    val diets: List<String?>?,
    @Json(name = "occasions")
    val occasions: List<String?>?,
    @Json(name = "analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstructionItem?>?,
    @Json(name = "spoonacularSourceUrl")
    val spoonacularSourceUrl: String?
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
data class AnalyzedInstructionItem(
    @Json(name = "name")
    val name: String?,
    @Json(name = "steps")
    val steps: List<StepItem?>?
)

@JsonClass(generateAdapter = true)
data class StepItem(
    @Json(name = "number")
    val number: Int?,
    @Json(name = "step")
    val step: String?,
    @Json(name = "ingredients")
    val ingredients: List<IngredientItem?>?,
    @Json(name = "equipment")
    val equipment: List<EquipmentItem?>?,
    @Json(name = "length")
    val length: Length?
)

@JsonClass(generateAdapter = true)
data class Ingredient(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "localizedName")
    val localizedName: String?,
    @Json(name = "image")
    val image: String?
)

@JsonClass(generateAdapter = true)
data class EquipmentItem(
    @Json(name = "id")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "localizedName")
    val localizedName: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "temperature")
    val temperature: Temperature?
)

@JsonClass(generateAdapter = true)
data class Temperature(
    @Json(name = "number")
    val number: Double?,
    @Json(name = "unit")
    val unit: String?
)

@JsonClass(generateAdapter = true)
data class Length(
    @Json(name = "number")
    val number: Int?,
    @Json(name = "unit")
    val unit: String?
)