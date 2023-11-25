package io.dev.relic.feature.pages.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.home.viewmodel.HomeViewModel
import io.dev.relic.feature.pages.home.viewmodel.state.HomeFoodRecipesDataState
import io.dev.relic.feature.pages.home.viewmodel.state.HomeWeatherDataState
import io.dev.relic.feature.pages.home.widget.HomeFoodRecipesPanel
import io.dev.relic.feature.pages.home.widget.HomeTopBar
import io.dev.relic.feature.pages.home.widget.HomeWeatherCard
import io.dev.relic.feature.screens.main.MainState
import io.core.ui.dialog.CommonItemDivider
import io.core.ui.theme.mainThemeColor

@Composable
fun HomePageRoute(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val mainState: MainState = mainViewModel.mainStateFlow.collectAsStateWithLifecycle().value
    val homeWeatherState: HomeWeatherDataState = homeViewModel.weatherDataStateFlow.collectAsStateWithLifecycle().value
    val homeFoodRecipesState: HomeFoodRecipesDataState = homeViewModel.foodRecipesDataStateFlow.collectAsStateWithLifecycle().value

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
        weatherDataState = homeWeatherState,
        foodRecipesState = homeFoodRecipesState,
        currentSelectedFoodRecipesTab = homeViewModel.currentSelectedFoodRecipesTab,
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
    weatherDataState: HomeWeatherDataState,
    foodRecipesState: HomeFoodRecipesDataState,
    currentSelectedFoodRecipesTab: Int,
    onWeatherRetry: () -> Unit,
    onFoodRecipesRetry: () -> Unit,
    onFetchMoreFoodRecipesData: () -> Unit,
    onSelectedFoodRecipesTabItem: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeTopBar(onOpenDrawer = {})
            Spacer(modifier = Modifier.height(32.dp))
            HomeWeatherCard(
                weatherDataState = weatherDataState,
                onRetryClick = onWeatherRetry
            )
            CommonItemDivider()
            HomeFoodRecipesPanel(
                currentSelectedTab = currentSelectedFoodRecipesTab,
                foodRecipesState = foodRecipesState,
                onRetryClick = onFoodRecipesRetry,
                onFetchMore = onFetchMoreFoodRecipesData,
                onTabItemClick = onSelectedFoodRecipesTabItem
            )
            CommonItemDivider()
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePagePreview() {
    HomePage(
        weatherDataState = HomeWeatherDataState.Init,
        foodRecipesState = HomeFoodRecipesDataState.Init,
        currentSelectedFoodRecipesTab = 0,
        onWeatherRetry = {},
        onFoodRecipesRetry = {},
        onFetchMoreFoodRecipesData = {},
        onSelectedFoodRecipesTabItem = { _: Int, _: String -> }
    )
}