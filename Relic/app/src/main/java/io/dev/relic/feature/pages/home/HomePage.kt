package io.dev.relic.feature.pages.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.home.viewmodel.HomeViewModel
import io.dev.relic.feature.pages.home.viewmodel.state.HomeFoodRecipesDataState
import io.dev.relic.feature.pages.home.viewmodel.state.HomeWeatherDataState
import io.dev.relic.feature.screens.main.MainState

@Composable
fun HomePageRoute(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val mainState: MainState = mainViewModel.mainStateFlow.collectAsStateWithLifecycle().value
    val homeWeatherState: HomeWeatherDataState = homeViewModel.weatherDataStateFlow.collectAsStateWithLifecycle().value
    val homeFoodRecipesState: HomeFoodRecipesDataState = homeViewModel.foodRecipesDataStateFlow.collectAsStateWithLifecycle().value

    LaunchedEffect(
        key1 = mainState,
        block = {
            when (mainState) {
                is MainState.Init -> {
                    //
                }

                is MainState.Empty -> {

                }

                is MainState.AccessingLocation -> {

                }

                is MainState.AccessLocationFailed -> {

                }

                is MainState.AccessLocationSucceed -> {
                    mainState.location?.also {
                        homeViewModel.execRemoteWeatherDataFlow(it.latitude, it.longitude)
                    }
                }
            }
        }
    )

    HomePage(
        weatherDataState = homeWeatherState,
        foodRecipesState = homeFoodRecipesState
    )
}

@Composable
private fun HomePage(
    weatherDataState: HomeWeatherDataState,
    foodRecipesState: HomeFoodRecipesDataState
) {
    //
}

@Composable
@Preview(showBackground = true)
private fun HomePagePreview() {
    HomePage(
        weatherDataState = HomeWeatherDataState.Init,
        foodRecipesState = HomeFoodRecipesDataState.Init
    )
}