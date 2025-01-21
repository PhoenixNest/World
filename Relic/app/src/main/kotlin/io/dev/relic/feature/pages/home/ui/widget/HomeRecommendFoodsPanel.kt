package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.util.TimeUtil
import io.core.ui.theme.RelicAppTheme
import io.core.ui.theme.RelicFontFamily.googleSans
import io.core.ui.theme.commonLoadingShimmerColor
import io.core.ui.theme.placeHolderHighlightColor
import io.core.ui.widget.CommonAsyncImage
import io.data.model.food_recipes.FoodRecipesRandomModel
import io.dev.relic.R
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.util.FoodRecipesAutoConvertor

data class HomeRecommendFoodsPanelAction(
    val state: FoodRecipesDataState,
    @Stable val onRefreshClick: () -> Unit,
    @Stable val onItemClick: (model: FoodRecipesRandomModel) -> Unit
)

@Composable
fun HomeRecommendFoodsPanel(
    state: FoodRecipesDataState,
    action: HomeRecommendFoodsPanelAction,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        HomeRecommendFoodsPanelTitle(
            onRefreshClick = action.onRefreshClick,
            modifier = modifier
        )
        HomeRecommendFoodsList(
            state = state,
            onItemClick = action.onItemClick
        )
    }
}

@Composable
private fun HomeRecommendFoodsPanelTitle(
    onRefreshClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTimeSection = TimeUtil.getCurrentTimeSection()
    val dishType = FoodRecipesAutoConvertor.convertTimeSectionToDishType(currentTimeSection)
    val dishTypeLabel = stringResource(dishType.labelResId)
    val title = stringResource(R.string.food_recipes_recommend_title, dishTypeLabel)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeTitleBar(titleStr = title)
        IconButton(onClick = onRefreshClick) {
            Icon(
                painter = painterResource(R.drawable.ic_refresh),
                contentDescription = DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun HomeRecommendFoodsList(
    state: FoodRecipesDataState,
    onItemClick: (model: FoodRecipesRandomModel) -> Unit
) {
    when (state) {
        FoodRecipesDataState.Init,
        FoodRecipesDataState.Fetching -> {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(3) { index ->
                    val decorationModifier = Modifier.padding(
                        start = if (index == 0) 12.dp else 0.dp,
                        end = if (index == 2) 12.dp else 0.dp
                    )
                    item { HomeRecommendFoodItemPlaceholder(modifier = decorationModifier) }
                }
            }
        }

        is FoodRecipesDataState.Empty,
        is FoodRecipesDataState.NoFoodRecipesData,
        is FoodRecipesDataState.FetchFailed -> {

        }

        is FoodRecipesDataState.FetchSucceed<*> -> {
            val dataList = state.data
                    as? List<FoodRecipesRandomModel>

            if (dataList.isNullOrEmpty()) {
                return
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(dataList) { index, model ->
                    val decorationModifier = Modifier.padding(
                        start = if (index == 0) 12.dp else 0.dp,
                        end = if (index == dataList.size - 1) 12.dp else 0.dp
                    )
                    HomeRecommendFoodItem(
                        model = model,
                        onClick = onItemClick,
                        modifier = decorationModifier
                    )
                }
            }
        }
    }
}

@Composable
private fun HomeRecommendFoodItem(
    model: FoodRecipesRandomModel,
    onClick: (model: FoodRecipesRandomModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = { onClick.invoke(model) },
        modifier = modifier,
        color = Color.Transparent,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.width(120.dp),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonAsyncImage(
                url = model.image ?: "",
                imageWidth = 120.dp,
                imageHeight = 120.dp,
                imageRadius = 8.dp
            )
            Text(
                text = model.title ?: "Unknown author",
                modifier = Modifier.basicMarquee(),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = googleSans,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 4.dp,
                    alignment = Alignment.Start
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_cook_time),
                    contentDescription = DEFAULT_DESC,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "${model.cookTime ?: "--"} min",
                    color = MaterialTheme.colorScheme.onSurface,
                    fontFamily = googleSans,
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeRecommendFoodItemPreview() {
    RelicAppTheme {
        HomeRecommendFoodItem(
            model = FoodRecipesRandomModel(
                id = null,
                title = "Food Recipes",
                image = null,
                isVegan = true,
                healthScore = 10,
                cookTime = 10
            ),
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun HomeRecommendFoodItemPlaceholder(modifier: Modifier = Modifier) {
    RelicAppTheme {
        Surface(
            color = Color.Transparent,
            shape = RoundedCornerShape(12.dp),
            modifier = modifier
        ) {
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(150.dp)
                    .placeholder(
                        visible = true,
                        color = commonLoadingShimmerColor,
                        highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                    )
            )
        }
    }
}