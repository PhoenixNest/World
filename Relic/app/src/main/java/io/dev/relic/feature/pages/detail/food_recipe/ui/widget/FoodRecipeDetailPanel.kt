package io.dev.relic.feature.pages.detail.food_recipe.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.Common.UNKNOWN_VALUE_INT
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.common.util.StringUtil.formatHTML
import io.core.ui.CommonAsyncImage
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColor50
import io.core.ui.theme.mainTextColorDark
import io.core.ui.theme.mainThemeColorLight
import io.data.dto.food_recipes.get_recipes_information_by_id.ExtendedIngredientItem
import io.data.model.food_recipes.FoodRecipeInformationModel
import io.dev.relic.R

@Composable
fun FoodRecipeDetailPanel(model: FoodRecipeInformationModel) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        model.apply {
            Spacer(modifier = Modifier.height(16.dp))
            FoodRecipeTitleBar(
                title = title ?: UNKNOWN_VALUE_STRING,
                cookTime = readyInMinutes ?: UNKNOWN_VALUE_INT,
                healthScore = healthScore ?: UNKNOWN_VALUE_INT
            )
            Spacer(modifier = Modifier.height(16.dp))
            FoodRecipeSummary(summary = summary ?: UNKNOWN_VALUE_STRING)
            CommonItemDivider()
            FoodRecipeIngredientTitle()
            Spacer(modifier = Modifier.height(16.dp))
            FoodRecipeIngredientRow(ingredientList = extendedIngredients ?: emptyList())
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun FoodRecipeSummary(summary: String) {
    val annotatedString = buildAnnotatedString {
        append(formatHTML(summary))
    }
    Text(
        text = annotatedString,
        modifier = Modifier.padding(horizontal = 16.dp),
        style = TextStyle(
            color = mainTextColor50,
            fontSize = 14.sp,
            fontFamily = ubuntu,
            lineHeight = TextUnit(
                value = 1.6F,
                type = TextUnitType.Em
            )
        )
    )
}

@Composable
private fun FoodRecipeIngredientTitle() {
    Text(
        text = stringResource(R.string.food_recipes_ingredient_title),
        modifier = Modifier.padding(horizontal = 16.dp),
        style = TextStyle(
            color = mainTextColor,
            fontFamily = ubuntu,
            fontSize = 20.sp,
        )
    )
}

@Composable
private fun FoodRecipeIngredientRow(ingredientList: List<ExtendedIngredientItem?>) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(ingredientList) { index, item ->
            if (item == null) {
                //
            } else {
                val itemDecorationModifier: Modifier = Modifier.padding(
                    start = if (index == 0) 16.dp else 0.dp,
                    end = if (index == ingredientList.size - 1) 16.dp else 0.dp
                )
                FoodRecipeIngredientRowItem(
                    item = item,
                    modifier = itemDecorationModifier
                )
            }
        }
    }
}

@Composable
private fun FoodRecipeIngredientRowItem(
    item: ExtendedIngredientItem,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentSize()
            .background(
                color = mainThemeColorLight,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonAsyncImage(
            url = item.image,
            imageWidth = 64.dp,
            imageHeight = 64.dp,
            imageRadius = 12.dp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = item.name ?: UNKNOWN_VALUE_STRING,
            style = TextStyle(
                color = mainTextColorDark,
                fontFamily = ubuntu
            )
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282C34)
private fun FoodRecipeDetailPanelPreview() {
    FoodRecipeDetailPanel(
        model = FoodRecipeInformationModel(
            id = null,
            title = "Strawberry Mango Green Tea Limeade",
            vegetarian = null,
            vegan = true,
            glutenFree = null,
            dairyFree = null,
            veryHealthy = null,
            cheap = true,
            veryPopular = null,
            sustainable = null,
            lowFodmap = null,
            weightWatcherSmartPoints = null,
            gaps = null,
            preparationMinutes = null,
            cookingMinutes = null,
            aggregateLikes = null,
            healthScore = 10,
            creditsText = null,
            license = null,
            sourceName = null,
            pricePerServing = null,
            extendedIngredients = listOf(
                ExtendedIngredientItem(
                    id = null,
                    aisle = null,
                    image = null,
                    consistency = null,
                    name = "Egg",
                    nameClean = null,
                    original = null,
                    originalName = null,
                    amount = null,
                    unit = null,
                    meta = null,
                    measures = null
                ),
                ExtendedIngredientItem(
                    id = null,
                    aisle = null,
                    image = null,
                    consistency = null,
                    name = "Meat",
                    nameClean = null,
                    original = null,
                    originalName = null,
                    amount = null,
                    unit = null,
                    meta = null,
                    measures = null
                ),
                ExtendedIngredientItem(
                    id = null,
                    aisle = null,
                    image = null,
                    consistency = null,
                    name = "Onion",
                    nameClean = null,
                    original = null,
                    originalName = null,
                    amount = null,
                    unit = null,
                    meta = null,
                    measures = null
                ),
                ExtendedIngredientItem(
                    id = null,
                    aisle = null,
                    image = null,
                    consistency = null,
                    name = "Parsley",
                    nameClean = null,
                    original = null,
                    originalName = null,
                    amount = null,
                    unit = null,
                    meta = null,
                    measures = null
                )
            ),
            readyInMinutes = null,
            servings = 10,
            sourceUrl = "https://www.foodista.com/recipe/ZC3NFKZ8/strawberry-mango-green-tea-limeade",
            image = "https://spoonacular.com/recipeImages/661834-556x370.jpg",
            imageType = "jpg",
            nutrition = null,
            summary = "Strawberry Mango Green Tea Limeade is a Mexican beverage. This recipe serves 6 and costs \$12.79 per serving. One serving contains <b>74 calories</b>, <b>1g of protein</b>, and <b>0g of fat</b>. It is brought to you by Foodista. It is a good option if you're following a <b>gluten free, dairy free, lacto ovo vegetarian, and vegan</b> diet. If you have strawberries, mango, tea, and a few other ingredients on hand, you can make it. It will be a hit at your <b>Mother's Day</b> event. Only a few people made this recipe, and 3 would say it hit the spot. From preparation to the plate, this recipe takes about <b>45 minutes</b>. All things considered, we decided this recipe <b>deserves a spoonacular score of 35%</b>. This score is not so outstanding. Users who liked this recipe also liked <a href=\\\"https://spoonacular.com/recipes/jasmine-green-iced-tea-limeade-607099\\\">Jasmine Green Iced Tea Limeade</a>, <a href=\\\"https://spoonacular.com/recipes/mango-green-tea-smoothie-708914\\\">Mango Green Tea Smoothie</a>, and <a href=\\\"https://spoonacular.com/recipes/green-tea-mango-splash-59080\\\">Green Tea & Mango Splash</a>.",
            cuisines = null,
            dishTypes = null,
            diets = null,
            occasions = null,
            winePairing = null,
            instructions = null,
            analyzedInstructions = null
        )
    )
}