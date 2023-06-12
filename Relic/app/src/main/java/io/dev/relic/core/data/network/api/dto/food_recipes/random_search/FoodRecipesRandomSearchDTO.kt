package io.dev.relic.core.data.network.api.dto.food_recipes.random_search

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import io.dev.relic.core.data.network.api.dto.food_recipes.AnalyzedInstruction
import io.dev.relic.core.data.network.api.dto.food_recipes.ExtendedIngredient

/**
 * Json sample data:
 *
 * ```
 * {
 *     "recipes": [
 *         {
 *             "vegetarian": false,
 *             "vegan": false,
 *             "glutenFree": true,
 *             "dairyFree": false,
 *             "veryHealthy": false,
 *             "cheap": false,
 *             "veryPopular": false,
 *             "sustainable": false,
 *             "lowFodmap": false,
 *             "weightWatcherSmartPoints": 6,
 *             "gaps": "no",
 *             "preparationMinutes": -1,
 *             "cookingMinutes": -1,
 *             "aggregateLikes": 4,
 *             "healthScore": 30,
 *             "creditsText": "foodista.com",
 *             "sourceName": "foodista.com",
 *             "pricePerServing": 309.37,
 *             "extendedIngredients": [
 *                 {
 *                     "id": 12087,
 *                     "aisle": "Nuts;Health Foods",
 *                     "image": "cashews.jpg",
 *                     "consistency": "SOLID",
 *                     "name": "cashews",
 *                     "nameClean": "cashews",
 *                     "original": "½ cup cashews, roughly chopped",
 *                     "originalName": "cashews, roughly chopped",
 *                     "amount": 0.5,
 *                     "unit": "cup",
 *                     "meta": [
 *                         "roughly chopped"
 *                     ],
 *                     "measures": {
 *                         "us": {
 *                             "amount": 0.5,
 *                             "unitShort": "cups",
 *                             "unitLong": "cups"
 *                         },
 *                         "metric": {
 *                             "amount": 118.294,
 *                             "unitShort": "ml",
 *                             "unitLong": "milliliters"
 *                         }
 *                     }
 *                 },
 *                 ...
 *             ],
 *             "id": 645384,
 *             "title": "Greek Yogurt Chicken Salad",
 *             "readyInMinutes": 30,
 *             "servings": 4,
 *             "sourceUrl": "http://www.foodista.com/recipe/F7WPPMM3/greek-yogurt-chicken-salad",
 *             "image": "https://spoonacular.com/recipeImages/645384-556x370.jpg",
 *             "imageType": "jpg",
 *             "summary": "Greek Yogurt Chicken Salad is a Mediterranean main course. This recipe serves 4 and costs $3.09 per serving. Watching your figure? This gluten free and primal recipe has <b>334 calories</b>, <b>41g of protein</b>, and <b>11g of fat</b> per serving. This recipe from Foodista has 4 fans. Head to the store and pick up greek yogurt, chicken breasts, garlic powder, and a few other things to make it today. From preparation to the plate, this recipe takes approximately <b>30 minutes</b>. Taking all factors into account, this recipe <b>earns a spoonacular score of 73%</b>, which is solid. <a href=\"https://spoonacular.com/recipes/greek-yogurt-chicken-salad-1321001\">Greek Yogurt Chicken Salad</a>, <a href=\"https://spoonacular.com/recipes/greek-yogurt-chicken-salad-1102192\">Greek Yogurt Chicken Salad</a>, and <a href=\"https://spoonacular.com/recipes/greek-yogurt-chicken-salad-1342611\">Greek Yogurt Chicken Salad</a> are very similar to this recipe.",
 *             "cuisines": [
 *                 "Mediterranean",
 *                 ...
 *             ],
 *             "dishTypes": [
 *                 "lunch",
 *                 ...
 *             ],
 *             "diets": [
 *                 "gluten free",
 *                 "primal"
 *             ],
 *             "occasions": [],
 *             "instructions": "<ol><li>Start by cooking some chicken breasts. I like boiling them in chicken broth or stock, but feel free to boil in water, too. Boiling the chicken in broth, however, will give it significantly more flavor. Bring the chicken broth/stock to a boil and put in whole chicken breasts. Cook until no pink remains. Depending on the size of the chicken breasts, this could take anywhere from 15 - 20 minutes.</li><li>While the chicken is boiling, put together your sauce. Whisk together greek yogurt, dijon mustard, and garlic powder together until well mixed. Add in salt and pepper, to taste.</li><li>Next, stir in dried cranberries or raisins and cashews. If you're worried about the cashews getting too soft, you could add them right before serving!</li><li>After the chicken is done boiling, I like to let it rest for about 5 minutes. This ensures that the juice stays in the chicken instead of spilling out when you cut it!</li><li>Dice up the chicken and mix it into the sauce. Serve.</li></ol>",
 *             "analyzedInstructions": [
 *                 {
 *                     "name": "",
 *                     "steps": [
 *                         {
 *                             "number": 1,
 *                             "step": "Start by cooking some chicken breasts. I like boiling them in chicken broth or stock, but feel free to boil in water, too. Boiling the chicken in broth, however, will give it significantly more flavor. Bring the chicken broth/stock to a boil and put in whole chicken breasts. Cook until no pink remains. Depending on the size of the chicken breasts, this could take anywhere from 15 - 20 minutes.While the chicken is boiling, put together your sauce.",
 *                             "ingredients": [
 *                                 {
 *                                     "id": 5062,
 *                                     "name": "chicken breast",
 *                                     "localizedName": "chicken breast",
 *                                     "image": "chicken-breasts.png"
 *                                 },
 *                                 ...
 *                             ],
 *                             "equipment": [],
 *                             "length": {
 *                                 "number": 20,
 *                                 "unit": "minutes"
 *                             }
 *                         },
 *                         ...
 *                     ]
 *                 }
 *             ],
 *             "originalId": null,
 *             "spoonacularSourceUrl": "https://spoonacular.com/greek-yogurt-chicken-salad-645384"
 *         }
 *     ]
 * }
 * ```
 * */
@JsonClass(generateAdapter = true)
data class FoodRecipesRandomSearchDTO(
    @Json(name = "recipes")
    val recipes: List<FoodRecipesRandomSearchResultDTO>
)

@JsonClass(generateAdapter = true)
data class FoodRecipesRandomSearchResultDTO(
    @Json(name = "vegetarian")
    val vegetarian: Boolean,
    @Json(name = "vegan")
    val vegan: Boolean,
    @Json(name = "glutenFree")
    val glutenFree: Boolean,
    @Json(name = "dairyFree")
    val dairyFree: Boolean,
    @Json(name = "veryHealthy")
    val veryHealthy: Boolean,
    @Json(name = "cheap")
    val cheap: Boolean,
    @Json(name = "veryPopular")
    val veryPopular: Boolean,
    @Json(name = "sustainable")
    val sustainable: Boolean,
    @Json(name = "lowFodmap")
    val lowFodmap: Boolean,
    @Json(name = "weightWatcherSmartPoints")
    val weightWatcherSmartPoints: Int,
    @Json(name = "gaps")
    val gaps: String,
    @Json(name = "preparationMinutes")
    val preparationMinutes: Int,
    @Json(name = "cookingMinutes")
    val cookingMinutes: Int,
    @Json(name = "aggregateLikes")
    val aggregateLikes: Int,
    @Json(name = "healthScore")
    val healthScore: Int,
    @Json(name = "creditsText")
    val creditsText: String,
    @Json(name = "license")
    val license: String,
    @Json(name = "sourceName")
    val sourceName: String,
    @Json(name = "pricePerServing")
    val pricePerServing: Double,
    @Json(name = "extendedIngredients")
    val extendedIngredients: List<ExtendedIngredient>,
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "readyInMinutes")
    val readyInMinutes: Int,
    @Json(name = "servings")
    val servings: Int,
    @Json(name = "sourceUrl")
    val sourceUrl: String,
    @Json(name = "image")
    val image: String,
    @Json(name = "imageType")
    val imageType: String,
    @Json(name = "summary")
    val summary: String,
    @Json(name = "cuisines")
    val cuisines: List<String>,
    @Json(name = "dishTypes")
    val dishTypes: List<String>,
    @Json(name = "diets")
    val diets: List<String>,
    @Json(name = "occasions")
    val occasions: List<Any>,
    @Json(name = "instructions")
    val instructions: String,
    @Json(name = "analyzedInstructions")
    val analyzedInstructions: List<AnalyzedInstruction>,
    @Json(name = "originalId")
    val originalId: Any?,
    @Json(name = "spoonacularSourceUrl")
    val spoonacularSourceUrl: String
)