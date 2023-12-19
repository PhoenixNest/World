package io.dev.relic.feature.pages.home.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.food_recipes.ui.FoodRecipesPanel
import io.dev.relic.feature.function.weather.WeatherDataState
import io.dev.relic.feature.function.weather.ui.WeatherCard

@Composable
fun HomePageContent(
    isShowWeatherCard: Boolean,
    isShowFoodRecipesCard: Boolean,
    weatherDataState: WeatherDataState,
    foodRecipesState: FoodRecipesDataState,
    foodRecipesTabLazyListState: LazyListState,
    foodRecipesContentLazyListState: LazyListState,
    currentSelectedFoodRecipesTab: Int,
    onOpenDrawer: () -> Unit,
    onWeatherRetry: () -> Unit,
    onFoodRecipesRetry: () -> Unit,
    onFetchMoreFoodRecipesData: () -> Unit,
    onSelectedFoodRecipesTabItem: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HomeTopBar(onOpenDrawer = onOpenDrawer)
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                if (isShowWeatherCard) {
                    WeatherCard(
                        weatherDataState = weatherDataState,
                        onRetryClick = onWeatherRetry
                    )
                    CommonItemDivider()
                }
            }
            item {
                if (isShowFoodRecipesCard) {
                    FoodRecipesPanel(
                        currentSelectedTab = currentSelectedFoodRecipesTab,
                        tabLazyListState = foodRecipesTabLazyListState,
                        contentLazyListState = foodRecipesContentLazyListState,
                        foodRecipesState = foodRecipesState,
                        onRetryClick = onFoodRecipesRetry,
                        onFetchMore = onFetchMoreFoodRecipesData,
                        onTabItemClick = onSelectedFoodRecipesTabItem
                    )
                    CommonItemDivider()
                }
            }
        }
    }
}