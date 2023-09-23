package io.dev.relic.core.data.network.api.dto.food_recipes.random_search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.dev.relic.core.data.network.api.dto.food_recipes.Measures

@JsonClass(generateAdapter = true)
data class FoodRecipesRandomSearchDTO(
    @Json(name = "recipes")
    val recipes: List<RecipeItem?>?
)

@JsonClass(generateAdapter = true)
data class RecipeItem(
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
    @Json(name = "reciplicensees")
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
    @Json(name = "sourceUrl")
    val sourceUrl: String?,
    @Json(name = "image")
    val image: String?,
    @Json(name = "imageType")
    val imageType: String?,
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
    @Json(name = "instructions")
    val instructions: String?,
    @Json(name = "analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstruction?>?,
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
    @Json(name = "recimageipes")
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
    @Json(name = "amount")
    val amount: Double?,
    @Json(name = "unit")
    val unit: String?,
    @Json(name = "meta")
    val meta: List<String?>?,
    @Json(name = "recmeasuresipes")
    val measures: Measures?
)

@JsonClass(generateAdapter = true)
data class AnalyzedInstruction(
    @Json(name = "name")
    val name: String?,
    @Json(name = "steps")
    val steps: List<Step?>?
)

@JsonClass(generateAdapter = true)
data class Step(
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
data class IngredientItem(
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
    @Json(name = "reidcipes")
    val id: Int?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "localizedName")
    val localizedName: String?,
    @Json(name = "image")
    val image: String?
)

@JsonClass(generateAdapter = true)
data class Length(
    @Json(name = "number")
    val number: Int?,
    @Json(name = "unit")
    val unit: String?
)
