package io.dev.relic.feature.function.food_recipes.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainBackgroundColorLight
import io.core.ui.theme.mainThemeColor
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel

@Composable
fun FoodRecipesCardList(
    lazyListState: LazyListState,
    modelList: List<FoodRecipesComplexSearchInfoModel?>
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        state = lazyListState,
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        itemsIndexed(items = modelList) { index: Int, data: FoodRecipesComplexSearchInfoModel? ->
            if (data == null) {
                //
            } else {
                val itemDecorationModifier: Modifier = Modifier.padding(
                    start = if (index == 0) 16.dp else 0.dp,
                    end = if (index == modelList.size - 1) 16.dp else 0.dp
                )
                FoodRecipesCardItem(
                    data = data,
                    modifier = itemDecorationModifier
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun FoodRecipesCardItem(
    data: FoodRecipesComplexSearchInfoModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(180.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = mainThemeColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CommonAsyncImage(
                url = data.image,
                imageWidth = 180.dp,
                imageHeight = 180.dp
            )
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = mainBackgroundColorLight,
                        shape = RoundedCornerShape(10.dp)
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = data.title ?: "",
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .basicMarquee(),
                    style = TextStyle(
                        color = mainThemeColor,
                        fontFamily = RelicFontFamily.ubuntu,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}