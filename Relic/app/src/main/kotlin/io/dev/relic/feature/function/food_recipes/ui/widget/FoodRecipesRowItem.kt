package io.dev.relic.feature.function.food_recipes.ui.widget

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.CommonAsyncImage
import io.data.model.food_recipes.FoodRecipesComplexSearchModel
import io.dev.relic.R

@Composable
fun FoodRecipesRowItem(
    data: FoodRecipesComplexSearchModel,
    onItemClick: (recipesData: FoodRecipesComplexSearchModel) -> Unit,
    modifier: Modifier = Modifier
) {
    val containerWidth = 120.dp
    val imageSize = 120.dp

    Column(
        modifier = modifier.width(containerWidth),
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        CommonAsyncImage(
            url = data.image,
            imageWidth = imageSize,
            imageHeight = imageSize,
            modifier = Modifier.clickable { onItemClick.invoke(data) },
            imageRadius = 12.dp
        )
        Text(
            text = data.title ?: "",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .basicMarquee(),
            style = MaterialTheme.typography.titleMedium
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            FoodRecipesDesc(
                iconResId = R.drawable.ic_cook_time,
                content = "${data.cookTime ?: -1} mins"
            )
            Spacer(modifier = Modifier.height(8.dp))
            FoodRecipesDesc(
                iconResId = R.drawable.ic_health_score,
                content = "${data.healthScore ?: -1} points"
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
            space = 4.dp,
            alignment = Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = content,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
@Preview(showBackground = true)
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