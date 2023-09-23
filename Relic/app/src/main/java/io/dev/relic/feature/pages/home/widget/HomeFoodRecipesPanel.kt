package io.dev.relic.feature.pages.home.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.feature.pages.home.viewmodel.state.HomeFoodRecipesDataState
import io.dev.relic.global.RelicConstants
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainBackgroundColorLight
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun HomeFoodRecipesPanel(
    foodRecipesState: HomeFoodRecipesDataState,
    onRefreshClick: () -> Unit,
    onFetchMore: () -> Unit
) {
    when (val state: HomeFoodRecipesDataState = foodRecipesState) {
        is HomeFoodRecipesDataState.Init,
        is HomeFoodRecipesDataState.Fetching -> {
            HomeFoodRecipesPanel(
                isLoading = true,
                modelList = emptyList(),
                onRefreshClick = {}
            )
        }

        is HomeFoodRecipesDataState.FetchSucceed -> {
            HomeFoodRecipesPanel(
                isLoading = false,
                modelList = state.modelList,
                onRefreshClick = onRefreshClick
            )
        }

        is HomeFoodRecipesDataState.Empty,
        is HomeFoodRecipesDataState.NoFoodRecipesData,
        is HomeFoodRecipesDataState.FetchFailed -> {
            HomeFoodRecipesPanel(
                isLoading = false,
                modelList = emptyList(),
                onRefreshClick = onRefreshClick
            )
        }
    }
}

@Composable
private fun HomeFoodRecipesPanel(
    isLoading: Boolean,
    modelList: List<FoodRecipesComplexSearchInfoModel?>?,
    onRefreshClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        HomeFoodRecipesPanelTitle()
        Spacer(modifier = Modifier.height(16.dp))
        HomeFoodRecipesCardList(
            isLoading = isLoading,
            modelList = modelList
        )
    }
}

@Composable
private fun HomeFoodRecipesPanelTitle() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .background(color = mainBackgroundColorLight)
    ) {

    }
}

@Composable
private fun HomeFoodRecipesCardList(
    isLoading: Boolean,
    modelList: List<FoodRecipesComplexSearchInfoModel?>?
) {
    if (modelList == null) {
        //
    } else {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .placeholder(
                    visible = isLoading,
                    shape = RoundedCornerShape(16.dp),
                    highlight = PlaceholderHighlight.shimmer()
                ),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterHorizontally
            ),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            )
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
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data.image)
                    .build(),
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    )
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
                        .fillMaxWidth()
                        .padding(8.dp)
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
        onRefreshClick = {}
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
        onRefreshClick = {}
    )
}