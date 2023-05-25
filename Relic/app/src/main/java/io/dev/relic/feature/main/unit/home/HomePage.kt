package io.dev.relic.feature.main.unit.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.dev.relic.R
import io.dev.relic.feature.main.unit.home.viewmodel.HomeUiState
import io.dev.relic.feature.main.unit.home.viewmodel.HomeViewModel
import io.dev.relic.feature.main.unit.home.widget.HomePageCardTitle
import io.dev.relic.feature.main.unit.home.widget.HomePageTopBar
import io.dev.relic.feature.main.unit.home.widget.card.HomeFoodRecipesCard
import io.dev.relic.feature.main.unit.home.widget.card.HomeTodoCard
import io.dev.relic.feature.main.unit.home.widget.card.HomeWeatherCard
import io.dev.relic.global.utils.LogUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal const val TAG = "HomePage"

@Composable
fun HomePageRoute(
    onNavigateToTodoPage: () -> Unit,
    onNavigateToWeatherDetailPage: () -> Unit,
    onNavigateToFoodRecipesDetailPage: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomePage(
        homeUiState = viewModel.homeUiState,
        onNavigateToTodoPage = onNavigateToTodoPage,
        onNavigateToWeatherDetailPage = onNavigateToWeatherDetailPage,
        onNavigateToFoodRecipesDetailPage = onNavigateToFoodRecipesDetailPage
    )
}

@Composable
private fun HomePage(
    homeUiState: HomeUiState,
    onNavigateToTodoPage: () -> Unit,
    onNavigateToWeatherDetailPage: () -> Unit,
    onNavigateToFoodRecipesDetailPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackBarHostState: SnackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        scaffoldState = ScaffoldState(
            drawerState = drawerState,
            snackbarHostState = snackBarHostState
        ),
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = if (drawerState.isClosed) ">>> Swipe >>>" else "<<< Swipe <<<")
                Spacer(Modifier.height(20.dp))
                Button(onClick = { coroutineScope.launch { drawerState.open() } }
                ) {
                    Text("Click to open")
                }
            }
        }
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = true
                )
                .background(color = Color.LightGray.copy(alpha = 0.1F)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            HomePageTopBar(
                onOpenDrawer = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                onNavigateToTodoPage = {
                    LogUtil.debug(TAG, "[HomePageTopBar]: Navigate to [Todo]")
                    onNavigateToTodoPage.invoke()
                }
            )
            Spacer(modifier = modifier.height(8.dp))
            // Weather
            HomePageCardTitle(titleResId = R.string.home_card_weather_title)
            Spacer(modifier = modifier.height(8.dp))
            HomeWeatherCard(
                weatherInfoModel = homeUiState.weatherInfo,
                onClick = onNavigateToWeatherDetailPage
            )
            // Todo
            HomePageCardTitle(titleResId = R.string.home_card_todo_title)
            Spacer(modifier = modifier.height(8.dp))
            HomeTodoCard(
                onClick = onNavigateToTodoPage
            )
            // Food recipes
            HomePageCardTitle(titleResId = R.string.home_card_food_recipes_title)
            Spacer(modifier = modifier.height(8.dp))
            HomeFoodRecipesCard(
                onClick = onNavigateToFoodRecipesDetailPage
            )
            Spacer(modifier = modifier.height(32.dp))
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePagePreview() {
    HomePage(
        onNavigateToTodoPage = {},
        onNavigateToWeatherDetailPage = {},
        onNavigateToFoodRecipesDetailPage = {},
        homeUiState = HomeUiState()
    )
}