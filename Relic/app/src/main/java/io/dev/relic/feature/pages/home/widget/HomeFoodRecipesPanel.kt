package io.dev.relic.feature.pages.home.widget

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.dev.relic.R
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.feature.pages.home.util.HomeFoodRecipesSimpleCategories
import io.dev.relic.feature.pages.home.viewmodel.state.HomeFoodRecipesDataState
import io.dev.relic.global.widget.CommonAsyncImage
import io.dev.relic.global.widget.CommonIconTextButton
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainBackgroundColor
import io.dev.relic.ui.theme.mainBackgroundColorLight
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainThemeColor
import io.dev.relic.ui.theme.placeHolderHighlightColor

@Composable
fun HomeFoodRecipesPanel(
    foodRecipesState: HomeFoodRecipesDataState,
    onRefreshClick: () -> Unit,
    onFetchMore: () -> Unit,
    onTabItemClick: (selectedItem: String) -> Unit
) {
    when (val state: HomeFoodRecipesDataState = foodRecipesState) {
        is HomeFoodRecipesDataState.Init,
        is HomeFoodRecipesDataState.Fetching -> {
            HomeFoodRecipesPanel(
                isLoading = true,
                modelList = emptyList(),
                onRefreshClick = {},
                onTabItemClick = {}
            )
        }

        is HomeFoodRecipesDataState.FetchSucceed -> {
            HomeFoodRecipesPanel(
                isLoading = false,
                modelList = state.modelList,
                onRefreshClick = onRefreshClick,
                onTabItemClick = onTabItemClick
            )
        }

        is HomeFoodRecipesDataState.Empty,
        is HomeFoodRecipesDataState.NoFoodRecipesData,
        is HomeFoodRecipesDataState.FetchFailed -> {
            HomeFoodRecipesPanel(
                isLoading = false,
                modelList = emptyList(),
                onRefreshClick = onRefreshClick,
                onTabItemClick = onTabItemClick
            )
        }
    }
}

@Composable
private fun HomeFoodRecipesPanel(
    isLoading: Boolean,
    modelList: List<FoodRecipesComplexSearchInfoModel?>?,
    onRefreshClick: () -> Unit,
    onTabItemClick: (selectedItem: String) -> Unit
) {
    if (modelList.isNullOrEmpty()) {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(300.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = mainBackgroundColorLight
        ) {
            //
        }
    } else {
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .placeholder(
                    visible = isLoading,
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(16.dp),
                    highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HomeFoodRecipesTabBar(onTabItemClick = onTabItemClick)
            Spacer(modifier = Modifier.height(16.dp))
            HomeFoodRecipesCardList(modelList = modelList)
        }
    }
}

@Composable
private fun HomeFoodRecipesCardList(modelList: List<FoodRecipesComplexSearchInfoModel?>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(
            items = modelList,
            key = { it?.id ?: -1 }
        ) { data: FoodRecipesComplexSearchInfoModel? ->
            if (data == null) {
                //
            } else {
                HomeFoodRecipesCardItem(data = data)
            }
        }
    }
}

@Composable
private fun HomeFoodRecipesTabBar(onTabItemClick: (selectedItem: String) -> Unit) {
    var currentSelectedTab: Int by remember {
        mutableIntStateOf(0)
    }

    Box(modifier = Modifier.height(136.dp)) {
        Text(
            text = stringResource(id = R.string.home_card_food_recipes_title),

            style = TextStyle(
                color = mainTextColor,
                fontSize = 24.sp,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HomeFoodRecipesSimpleCategories.entries.forEach { item ->
                HomeFoodRecipesTabItem(
                    isSelected = (currentSelectedTab == item.ordinal),
                    iconResId = item.selectedIconResId,
                    tabLabelResId = item.tabLabelResId,
                    onTabClick = {
                        currentSelectedTab = item.ordinal
                        onTabItemClick.invoke(it)
                    }
                )
            }
        }
    }

}

@Composable
private fun HomeFoodRecipesTabItem(
    isSelected: Boolean,
    @DrawableRes iconResId: Int,
    @StringRes tabLabelResId: Int,
    onTabClick: (selectedTab: String) -> Unit
) {
    val tabLabel: String = stringResource(id = tabLabelResId)
    CommonIconTextButton(
        iconResId = iconResId,
        labelResId = tabLabelResId,
        onClick = { onTabClick.invoke(tabLabel) },
        backgroundColor = if (isSelected) {
            mainBackgroundColorLight
        } else {
            mainBackgroundColor
        },
        shape = RoundedCornerShape(16.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HomeFoodRecipesCardItem(data: FoodRecipesComplexSearchInfoModel) {
    Card(
        modifier = Modifier.size(180.dp),
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
                        shape = RoundedCornerShape(12.dp)
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
        isLoading = false,
        modelList = null,
        onRefreshClick = {},
        onTabItemClick = {}
    )
}

@Composable
@Preview
private fun HomeFoodRecipesCardPreview() {
    HomeFoodRecipesPanel(
        isLoading = false,
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
        onRefreshClick = {},
        onTabItemClick = {}
    )
}