package io.dev.relic.core.data.network.api.dto.food_recipes.complex_search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Json data sample:
 *
 * ```
 * {
 *     "results": [
 *         {
 *             "id": 782585,
 *             "title": "Cannellini Bean and Asparagus Salad with Mushrooms",
 *             "image": "https://spoonacular.com/recipeImages/782585-312x231.jpg",
 *             "imageType": "jpg"
 *         },
 *         ...
 *     ],
 *     "offset": 0,
 *     "number": 10,
 *     "totalResults": 5221
 * }
 * ```
 *
 * @see FoodRecipesComplexSearchResultDTO
 * */
@JsonClass(generateAdapter = true)
data class FoodRecipesComplexSearchDTO(
    @field:Json(name = "results")
    val results: List<FoodRecipesComplexSearchResultDTO>,
    @field:Json(name = "offset")
    val offset: Int,
    @field:Json(name = "number")
    val number: Int,
    @field:Json(name = "totalResults")
    val totalResults: Int
)

/**
 * @see FoodRecipesComplexSearchDTO
 * */
@JsonClass(generateAdapter = true)
data class FoodRecipesComplexSearchResultDTO(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "title")
    val title: String,
    @field:Json(name = "image")
    val image: String,
    @field:Json(name = "imageType")
    val imageType: String
)