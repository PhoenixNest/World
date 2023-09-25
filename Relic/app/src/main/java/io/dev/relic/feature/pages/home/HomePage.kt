package io.dev.relic.feature.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.home.viewmodel.HomeViewModel
import io.dev.relic.feature.pages.home.viewmodel.state.HomeFoodRecipesDataState
import io.dev.relic.feature.pages.home.viewmodel.state.HomeWeatherDataState
import io.dev.relic.feature.pages.home.widget.HomeFoodRecipesPanel
import io.dev.relic.feature.pages.home.widget.HomeTopBar
import io.dev.relic.feature.pages.home.widget.HomeWeatherCard
import io.dev.relic.feature.screens.main.MainState
import io.dev.relic.global.widget.dialog.CommonItemDivider
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun HomePageRoute(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel
) {
    val mainState: MainState = mainViewModel.mainStateFlow.collectAsStateWithLifecycle().value
    val homeWeatherState: HomeWeatherDataState = homeViewModel.weatherDataStateFlow.collectAsStateWithLifecycle().value
    val homeFoodRecipesState: HomeFoodRecipesDataState = homeViewModel.foodRecipesDataStateFlow.collectAsStateWithLifecycle().value

    val lazyListState: LazyListState = rememberLazyListState()

    LaunchedEffect(mainState) {
        when (mainState) {
            is MainState.Init,
            is MainState.AccessingLocation -> {
                //
            }

            is MainState.Empty,
            is MainState.AccessLocationFailed -> {
                //
            }

            is MainState.AccessLocationSucceed -> {
                mainState.location?.also {
                    homeViewModel.fetchWeatherData(it.latitude, it.longitude)
                }
            }
        }
    }

    HomePage(
        lazyListState = lazyListState,
        weatherDataState = homeWeatherState,
        foodRecipesState = homeFoodRecipesState,
        currentSelectedFoodRecipesTabs = homeViewModel.currentSelectedFoodRecipesTab,
        onWeatherRetry = {
            mainViewModel.latestLocation?.also {
                homeViewModel.fetchWeatherData(it.latitude, it.longitude)
            }
        },
        onFoodRecipesRetry = {
            homeViewModel.fetchFoodRecipesData(isRefresh = true)
        },
        onFetchMoreFoodRecipesData = {
            homeViewModel.fetchFoodRecipesData(isRefresh = false)
        },
        onSelectedFoodRecipesTabItem = { currentSelectedTab: Int, selectedItem: String ->
            homeViewModel.apply {
                updateSelectedFoodRecipesTabs(currentSelectedTab)
                fetchFoodRecipesData(isRefresh = true, query = selectedItem)
            }
        }
    )
}

@Composable
private fun HomePage(
    lazyListState: LazyListState,
    weatherDataState: HomeWeatherDataState,
    foodRecipesState: HomeFoodRecipesDataState,
    currentSelectedFoodRecipesTabs: Int,
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
            state = lazyListState,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                HomeTopBar(onOpenDrawer = {})
                Spacer(modifier = Modifier.height(32.dp))
            }
            item {
                HomeWeatherCard(
                    weatherDataState = weatherDataState,
                    onRetryClick = onWeatherRetry
                )
                CommonItemDivider()
            }
            item {
                HomeFoodRecipesPanel(
                    currentSelectedFoodRecipesTabs,
                    foodRecipesState = foodRecipesState,
                    onRetryClick = onFoodRecipesRetry,
                    onFetchMore = onFetchMoreFoodRecipesData,
                    onTabItemClick = onSelectedFoodRecipesTabItem
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePagePreview() {
    HomePage(
        lazyListState = rememberLazyListState(),
        weatherDataState = HomeWeatherDataState.Init,
        foodRecipesState = HomeFoodRecipesDataState.Init,
        currentSelectedFoodRecipesTabs = 0,
        onWeatherRetry = {},
        onFoodRecipesRetry = {},
        onFetchMoreFoodRecipesData = {},
        onSelectedFoodRecipesTabItem = { _: Int, _: String -> }
    )
}