package io.dev.relic.feature.pages.home

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.data.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.food_recipes.FoodRecipesDataState
import io.dev.relic.feature.pages.home.ui.HomePageContent
import io.dev.relic.feature.pages.home.viewmodel.HomeViewModel
import io.dev.relic.feature.screens.main.MainScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomePageRoute(
    mainScreenState: MainScreenState,
    drawerState: DrawerState,
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    /* ======================== Field ======================== */

    // Food recipes
    val foodRecipesState by homeViewModel.foodRecipesDataStateFlow.collectAsStateWithLifecycle()

    /* ======================== Ui ======================== */

    val foodRecipesTabLazyListState = rememberLazyListState()
    val foodRecipesContentLazyListState = rememberLazyListState()

    HomePage(
        drawerState = drawerState,
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
        onFoodRecipesRetry = {
            homeViewModel.getFoodRecipesData(true)
        }
    )
}

@Composable
private fun HomePage(
    drawerState: DrawerState,
    foodRecipesState: FoodRecipesDataState,
    foodRecipesTabLazyListState: LazyListState,
    foodRecipesContentLazyListState: LazyListState,
    currentSelectedFoodRecipesTab: Int,
    onFetchMoreFoodRecipesData: () -> Unit,
    onFoodRecipesCardClick: (recipesData: FoodRecipesComplexSearchInfoModel) -> Unit,
    onFoodRecipesRetry: () -> Unit,
    onSelectedFoodRecipesTabItem: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    HomePageContent(
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
        onFoodRecipesRetry = onFoodRecipesRetry
    )
}