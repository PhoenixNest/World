package io.dev.relic.feature.pages.detail.food_recipe.ui.widget

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.Common.UNKNOWN_VALUE_STRING
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily.googleSans
import io.data.dto.food_recipes.get_recipes_information_by_id.ExtendedIngredientItem
import io.dev.relic.R

@Composable
fun FoodRecipeIngredientsRow(
    ingredientList: List<ExtendedIngredientItem?>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(R.string.food_recipes_ingredient_title),
            modifier = modifier,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = googleSans,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
        LazyRow(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            itemsIndexed(ingredientList.filterNotNull()) { index, item ->
                val itemDecorationModifier: Modifier = Modifier.padding(
                    start = if (index == 0) 12.dp else 0.dp,
                    end = if (index == ingredientList.size - 1) 12.dp else 0.dp
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
    Surface(
        modifier = modifier,
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.width(72.dp),
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
                modifier = Modifier.basicMarquee(),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontFamily = googleSans,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}