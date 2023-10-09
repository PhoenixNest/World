package io.dev.relic.feature.pages.home.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.dev.relic.R
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.feature.pages.home.util.HomeFoodRecipesSimpleCategories
import io.dev.relic.feature.pages.home.viewmodel.state.HomeFoodRecipesDataState
import io.dev.relic.global.widget.CommonAsyncImage
import io.dev.relic.global.widget.CommonRetryComponent
import io.dev.relic.global.widget.CommonVerticalIconTextButton
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainBackgroundColorLight
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainTextColorDark
import io.dev.relic.ui.theme.mainThemeColor
import io.dev.relic.ui.theme.mainThemeColorAccent
import io.dev.relic.ui.theme.placeHolderHighlightColor

@Composable
fun HomeFoodRecipesPanel(
    currentSelectedTab: Int,
    foodRecipesState: HomeFoodRecipesDataState,
    onRetryClick: () -> Unit,
    onFetchMore: () -> Unit,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    val lazyListState: LazyListState = rememberLazyListState()

    when (val state: HomeFoodRecipesDataState = foodRecipesState) {
        is HomeFoodRecipesDataState.Init,
        is HomeFoodRecipesDataState.Fetching -> {
            HomeFoodRecipesPanel(
                currentSelectedTab = currentSelectedTab,
                isFetchingData = true,
                lazyListState = lazyListState,
                modelList = emptyList(),
                onRetryClick = {},
                onTabItemClick = { _: Int, _: String -> }
            )
        }

        is HomeFoodRecipesDataState.FetchSucceed -> {
            HomeFoodRecipesPanel(
                currentSelectedTab = currentSelectedTab,
                isFetchingData = false,
                lazyListState = lazyListState,
                modelList = state.modelList,
                onRetryClick = onRetryClick,
                onTabItemClick = onTabItemClick
            )
        }

        is HomeFoodRecipesDataState.Empty,
        is HomeFoodRecipesDataState.NoFoodRecipesData,
        is HomeFoodRecipesDataState.FetchFailed -> {
            HomeFoodRecipesPanel(
                currentSelectedTab = currentSelectedTab,
                isFetchingData = false,
                lazyListState = lazyListState,
                modelList = emptyList(),
                onRetryClick = onRetryClick,
                onTabItemClick = onTabItemClick
            )
        }
    }
}

@Composable
private fun HomeFoodRecipesPanel(
    currentSelectedTab: Int,
    isFetchingData: Boolean,
    lazyListState: LazyListState,
    modelList: List<FoodRecipesComplexSearchInfoModel?>?,
    onRetryClick: () -> Unit,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        HomeFoodRecipesTabBar(
            currentSelectedTab = currentSelectedTab,
            onTabItemClick = onTabItemClick
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (isFetchingData) {
            Box(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .height(196.dp)
                    .background(
                        color = mainBackgroundColorLight,
                        RoundedCornerShape(16.dp)
                    )
                    .placeholder(
                        visible = true,
                        color = Color.DarkGray,
                        shape = RoundedCornerShape(16.dp),
                        highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                    )
            )
        } else {
            if (modelList.isNullOrEmpty()) {
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    CommonRetryComponent(
                        onRetryClick = onRetryClick,
                        containerHeight = 196.dp
                    )
                }
            } else {
                HomeFoodRecipesCardList(
                    lazyListState = lazyListState,
                    modelList = modelList
                )
            }
        }
    }
}

@Composable
private fun HomeFoodRecipesCardList(
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
                HomeFoodRecipesCardItem(
                    data = data,
                    modifier = itemDecorationModifier
                )
            }
        }
    }
}

@Composable
private fun HomeFoodRecipesTabBar(
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(104.dp)
                .align(Alignment.BottomCenter),
            backgroundColor = mainBackgroundColorLight,
            shape = RoundedCornerShape(16.dp)
        ) {
            LazyRow(
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(HomeFoodRecipesSimpleCategories.entries) { index: Int, item: HomeFoodRecipesSimpleCategories ->
                    val tabLabel: String = stringResource(id = item.tabLabelResId)
                    val itemDecorationModifier: Modifier = Modifier.padding(
                        start = if (index == 0) 16.dp else 0.dp,
                        end = if (index == HomeFoodRecipesSimpleCategories.entries.size - 1) 16.dp else 0.dp
                    )
                    HomeFoodRecipesTabItem(
                        isSelected = (currentSelectedTab == index),
                        iconResId = item.iconResId,
                        tabLabelResId = item.tabLabelResId,
                        onTabClick = { onTabItemClick.invoke(index, tabLabel) },
                        modifier = itemDecorationModifier
                    )
                }
            }
        }
        Card(
            modifier = Modifier.padding(horizontal = 8.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = mainThemeColorAccent
        ) {
            Text(
                text = stringResource(R.string.home_card_food_recipes_title),
                modifier = Modifier.padding(
                    horizontal = 12.dp,
                    vertical = 8.dp
                ),
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = RelicFontFamily.ubuntu,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }

}

@Composable
private fun HomeFoodRecipesTabItem(
    isSelected: Boolean,
    @DrawableRes iconResId: Int,
    @StringRes tabLabelResId: Int,
    onTabClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        CommonVerticalIconTextButton(
            iconResId = iconResId,
            labelResId = tabLabelResId,
            onClick = onTabClick,
            backgroundColor = if (isSelected) {
                mainThemeColorAccent
            } else {
                Color.Transparent
            },
            textColor = if (isSelected) {
                mainTextColor
            } else {
                mainTextColorDark
            },
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeFoodRecipesCardItem(
    data: FoodRecipesComplexSearchInfoModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.size(180.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = mainThemeColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            CommonAsyncImage(data.image)
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = mainBackgroundColorLight,
                        shape = RoundedCornerShape(16.dp)
                    ),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = data.title ?: "",
                    modifier = Modifier
                        .padding(8.dp)
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

@Composable
@Preview
private fun HomeFoodRecipesNoDataCardPreview() {
    HomeFoodRecipesPanel(
        currentSelectedTab = 0,
        isFetchingData = false,
        lazyListState = rememberLazyListState(),
        modelList = null,
        onRetryClick = {},
        onTabItemClick = { _: Int, _: String -> }
    )
}

@Composable
@Preview
private fun HomeFoodRecipesCardPreview() {
    HomeFoodRecipesPanel(
        currentSelectedTab = 0,
        isFetchingData = false,
        lazyListState = rememberLazyListState(),
        modelList = listOf(
            FoodRecipesComplexSearchInfoModel(
                id = -1,
                title = "Food Recipes 1",
                image = "xxx",
                imageType = "xxx"
            ),
            FoodRecipesComplexSearchInfoModel(
                id = -2,
                title = "Food Recipes 2 | Food Recipes 2 | Food Recipes 2",
                image = "xxx",
                imageType = "xxx"
            )
        ),
        onRetryClick = {},
        onTabItemClick = { _: Int, _: String -> }
    )
}