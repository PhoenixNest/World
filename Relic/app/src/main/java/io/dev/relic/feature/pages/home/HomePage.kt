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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.util.LogUtil
import io.core.ui.theme.mainThemeColor
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.function.weather.WeatherDataState
import io.dev.relic.feature.pages.home.ui.HomePageContent
import io.dev.relic.feature.pages.home.ui.widget.HomeDrawer
import io.dev.relic.feature.pages.home.viewmodel.HomeViewModel
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
                    homeViewModel.getWeatherData(it.latitude, it.longitude)
                }
            }
        }
    }

    HomePage(
        weatherDataState = weatherState,
        foodRecipesState = foodRecipesState,
        foodRecipesTabLazyListState = foodRecipesTabLazyListState,
        foodRecipesContentLazyListState = foodRecipesContentLazyListState,
        currentSelectedFoodRecipesTab = homeViewModel.getSelectedFoodRecipesTab(),
        onSelectedFoodRecipesTabItem = { currentSelectedTab, selectedItem ->
            homeViewModel.apply {
                updateSelectedFoodRecipesTab(currentSelectedTab)
                getFoodRecipesData(
                    isRefresh = true,
                    query = selectedItem
                )
            }
        },
        onFetchMoreFoodRecipesData = {
            homeViewModel.getFoodRecipesData(false)
        },
        onFoodRecipesCardClick = {

        },
        onWeatherRetry = {
            mainViewModel.latestLocation?.also {
                homeViewModel.getWeatherData(it.latitude, it.longitude)
            }
        },
        onFoodRecipesRetry = {
            homeViewModel.getFoodRecipesData(true)
        }
    )
}

@Composable
private fun HomePage(
    weatherDataState: WeatherDataState,
    foodRecipesState: FoodRecipesDataState,
    foodRecipesTabLazyListState: LazyListState,
    foodRecipesContentLazyListState: LazyListState,
    currentSelectedFoodRecipesTab: Int,
    onFetchMoreFoodRecipesData: () -> Unit,
    onFoodRecipesCardClick: (recipesData: FoodRecipesComplexSearchInfoModel) -> Unit,
    onWeatherRetry: () -> Unit,
    onFoodRecipesRetry: () -> Unit,
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
                weatherDataState = weatherDataState,
                foodRecipesState = foodRecipesState,
                foodRecipesTabLazyListState = foodRecipesTabLazyListState,
                currentSelectedFoodRecipesTab = currentSelectedFoodRecipesTab,
                onOpenDrawer = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                onSelectedFoodRecipesTabItem = onSelectedFoodRecipesTabItem,
                onFoodRecipesCardClick = onFoodRecipesCardClick,
                onWeatherRetry = onWeatherRetry,
                onFoodRecipesRetry = onFoodRecipesRetry
            )
        }
    )
}