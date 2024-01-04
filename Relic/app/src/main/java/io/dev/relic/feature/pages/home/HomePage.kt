package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.util.LogUtil
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.weather.WeatherDataState
import io.dev.relic.feature.pages.home.HomePageConfig.IS_SHOW_FOOD_RECIPES_CARD
import io.dev.relic.feature.pages.home.HomePageConfig.IS_SHOW_WEATHER_CARD
import io.dev.relic.feature.pages.home.viewmodel.HomeViewModel
import io.dev.relic.feature.pages.home.widget.HomeDrawer
import io.dev.relic.feature.pages.home.widget.HomePageContent
import io.dev.relic.feature.screens.main.MainScreenState
import io.dev.relic.feature.screens.main.MainState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomePageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    /* ======================== Field ======================== */

    // Location
    val mainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()

    // Weather
    val weatherState by homeViewModel.weatherDataStateFlow.collectAsStateWithLifecycle()

    // Food recipes
    val foodRecipesState by homeViewModel.foodRecipesDataStateFlow.collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val foodRecipesTabLazyListState = rememberLazyListState()
    val foodRecipesContentLazyListState = rememberLazyListState()

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
                val state = (mainState as MainState.AccessLocationSucceed)
                state.location?.also {
                    homeViewModel.fetchWeatherData(it.latitude, it.longitude)
                }
            }
        }
    }

    HomePage(
        isShowWeatherCard = IS_SHOW_WEATHER_CARD,
        isShowFoodRecipesCard = IS_SHOW_FOOD_RECIPES_CARD,
        weatherDataState = weatherState,
        foodRecipesState = foodRecipesState,
        foodRecipesTabLazyListState = foodRecipesTabLazyListState,
        foodRecipesContentLazyListState = foodRecipesContentLazyListState,
        currentSelectedFoodRecipesTab = homeViewModel.getSelectedFoodRecipesTab(),
        onWeatherRetry = {
            mainViewModel.latestLocation?.also {
                homeViewModel.fetchWeatherData(it.latitude, it.longitude)
            }
        },
        onFoodRecipesRetry = {
            homeViewModel.fetchFoodRecipesData(true)
        },
        onFetchMoreFoodRecipesData = {
            homeViewModel.fetchFoodRecipesData(false)
        },
        onSelectedFoodRecipesTabItem = { currentSelectedTab, selectedItem ->
            homeViewModel.apply {
                updateSelectedFoodRecipesTab(currentSelectedTab)
                fetchFoodRecipesData(
                    isRefresh = true,
                    query = selectedItem
                )
            }
        }
    )
}

@Composable
private fun HomePage(
    isShowWeatherCard: Boolean,
    isShowFoodRecipesCard: Boolean,
    weatherDataState: WeatherDataState,
    foodRecipesState: FoodRecipesDataState,
    foodRecipesTabLazyListState: LazyListState,
    foodRecipesContentLazyListState: LazyListState,
    currentSelectedFoodRecipesTab: Int,
    onWeatherRetry: () -> Unit,
    onFoodRecipesRetry: () -> Unit,
    onFetchMoreFoodRecipesData: () -> Unit,
    onSelectedFoodRecipesTabItem: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed,
        confirmStateChange = { drawerValue ->
            LogUtil.d("HomePage", "[Drawer] State: ${drawerValue.name}")
            true
        }
    )

    ModalDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeDrawer()
        },
        drawerBackgroundColor = mainThemeColor,
        content = {
            HomePageContent(
                isShowWeatherCard = isShowWeatherCard,
                isShowFoodRecipesCard = isShowFoodRecipesCard,
                weatherDataState = weatherDataState,
                foodRecipesState = foodRecipesState,
                foodRecipesTabLazyListState = foodRecipesTabLazyListState,
                foodRecipesContentLazyListState = foodRecipesContentLazyListState,
                currentSelectedFoodRecipesTab = currentSelectedFoodRecipesTab,
                onOpenDrawer = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                onWeatherRetry = onWeatherRetry,
                onFoodRecipesRetry = onFoodRecipesRetry,
                onFetchMoreFoodRecipesData = onFetchMoreFoodRecipesData,
                onSelectedFoodRecipesTabItem = onSelectedFoodRecipesTabItem
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
private fun HomePagePreview() {
    HomePage(
        isShowWeatherCard = IS_SHOW_WEATHER_CARD,
        isShowFoodRecipesCard = IS_SHOW_FOOD_RECIPES_CARD,
        weatherDataState = WeatherDataState.Init,
        foodRecipesState = FoodRecipesDataState.Init,
        foodRecipesTabLazyListState = rememberLazyListState(),
        foodRecipesContentLazyListState = rememberLazyListState(),
        currentSelectedFoodRecipesTab = 0,
        onWeatherRetry = {},
        onFoodRecipesRetry = {},
        onFetchMoreFoodRecipesData = {},
        onSelectedFoodRecipesTabItem = { _: Int, _: String -> }
    )
}