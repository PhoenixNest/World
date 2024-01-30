package io.dev.relic.feature.function.food_recipes.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainTextColor
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.R

@Composable
fun FoodRecipesCardItem(
    data: FoodRecipesComplexSearchInfoModel,
    onCardClick: (recipesData: FoodRecipesComplexSearchInfoModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.Transparent
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clickable { onCardClick.invoke(data) },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            CommonAsyncImage(
                url = data.image,
                imageWidth = (screenWidth / 3),
                imageHeight = (screenWidth / 3)
            )
            FoodRecipesIntro(data = data)
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun FoodRecipesIntro(data: FoodRecipesComplexSearchInfoModel) {
    Column(
        modifier = Modifier
            .padding(
                start = 12.dp,
                top = 12.dp
            )
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = data.title ?: "",
            modifier = Modifier.fillMaxWidth(),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                color = mainTextColor,
                fontSize = 16.sp,
                fontFamily = ubuntu,
                fontWeight = FontWeight.Bold,
                lineHeight = TextUnit(
                    value = 1.6F,
                    type = TextUnitType.Em
                )
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "By ${data.author ?: "Author"}",
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(),
            style = TextStyle(
                color = mainTextColor.copy(alpha = 0.5F),
                fontFamily = ubuntu
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        FoodRecipesDescPanel(data = data)
    }
}

@Composable
private fun FoodRecipesDescPanel(data: FoodRecipesComplexSearchInfoModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        FoodRecipesDescItem(
            iconResId = R.drawable.ic_cook_time,
            content = "${data.cookTime} min",
            modifier = Modifier.weight(1F)
        )
        Spacer(modifier = Modifier.width(4.dp))
        FoodRecipesDescItem(
            iconResId = R.drawable.ic_vegan,
            content = "${data.healthScore}",
            modifier = Modifier.weight(1F)
        )
    }
}

@Composable
private fun FoodRecipesDescItem(
    @DrawableRes iconResId: Int,
    content: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier.size(22.dp),
            tint = mainIconColorLight
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = content,
            style = TextStyle(
                color = mainTextColor,
                fontSize = 14.sp,
                fontFamily = ubuntu
            )
        )
    }
}

@Composable
@Preview
private fun FoodRecipesCardItemPreview() {
    FoodRecipesCardItem(
        data = FoodRecipesComplexSearchInfoModel(
            id = 0,
            title = "Coffee Cookies",
            author = "Foodista.com – The Cooking Encyclopedia Everyone Can Edit",
            image = "https://spoonacular.com/recipeImages/639865-312x231.jpg",
            isVegan = true,
            healthScore = 10,
            cookTime = 45
        ),
        onCardClick = {}
    )
}