package io.dev.relic.feature.function.food_recipes.ui.widget

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColorAccent
import io.core.ui.utils.RelicUiUtil.getCurrentScreenWidthDp
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FoodRecipesRowItem(
    data: FoodRecipesComplexSearchModel,
    onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = getCurrentScreenWidthDp()
    val containerWidth = screenWidth / 2
    val imageSize = screenWidth / 2

    Column(
        modifier = modifier.width(containerWidth),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Surface(
            modifier = Modifier.wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            color = Color.Transparent
        ) {
            CommonAsyncImage(
                url = data.image,
                imageWidth = imageSize,
                imageHeight = imageSize,
                modifier = Modifier.clickable { onItemClick.invoke(data) }
            )
        }
        Text(
            text = data.title ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .basicMarquee(),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 18.sp,
                fontFamily = ubuntu,
                textAlign = TextAlign.Center
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FoodRecipesDesc(
                iconResId = R.drawable.ic_cook_time,
                content = "${data.cookTime ?: -1} mins"
            )
            FoodRecipesDesc(
                iconResId = R.drawable.ic_health_score,
                content = "${data.healthScore ?: -1}"
            )
        }
    }
}

@Composable
private fun FoodRecipesDesc(
    @DrawableRes iconResId: Int,
    content: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.wrapContentSize(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = DEFAULT_DESC,
            tint = mainThemeColorAccent
        )
        Text(
            text = content,
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )
    }
}

@Composable
@Preview
private fun FoodRecipesCardItemPreview() {
    FoodRecipesRowItem(
        data = FoodRecipesComplexSearchModel(
            id = 0,
            title = "Coffee Cookies",
            author = "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
            image = "https://spoonacular.com/recipeImages/639865-312x231.jpg",
            isVegan = true,
            healthScore = 10,
            cookTime = 45
        ),
        onItemClick = {}
    )
}