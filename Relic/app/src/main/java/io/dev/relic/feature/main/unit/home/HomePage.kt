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
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import io.dev.relic.feature.main.unit.home.viewmodel.HomeUiState
import io.dev.relic.feature.main.unit.home.viewmodel.HomeViewModel
import io.dev.relic.feature.main.unit.home.widget.HomePageTopBar
import io.dev.relic.feature.main.unit.home.widget.card.HomeFoodRecipesCard
import io.dev.relic.feature.main.unit.home.widget.card.HomeWeatherCard

internal const val TAG = "HomePage"

@Composable
fun HomePageRoute(
    onNavigateToSubscribePage: () -> Unit,
    onNavigateToSettingPage: () -> Unit,
    onNavigateToCreateTodoPage: () -> Unit,
    onNavigateToWeatherDetailPage: () -> Unit,
    onNavigateToFoodRecipesDetailPage: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    HomePage(
        homeUiState = viewModel.homeUiState,
        onNavigateToSubscribePage = onNavigateToSubscribePage,
        onNavigateToSettingPage = onNavigateToSettingPage,
        onNavigateToCreateTodoPage = onNavigateToCreateTodoPage,
        onNavigateToWeatherDetailPage = onNavigateToWeatherDetailPage,
        onNavigateToFoodRecipesDetailPage = onNavigateToFoodRecipesDetailPage
    )
}

@Composable
private fun HomePage(
    homeUiState: HomeUiState,
    onNavigateToSubscribePage: () -> Unit,
    onNavigateToSettingPage: () -> Unit,
    onNavigateToCreateTodoPage: () -> Unit,
    onNavigateToWeatherDetailPage: () -> Unit,
    onNavigateToFoodRecipesDetailPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
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
                onNavigateToSubscribePage = onNavigateToSubscribePage,
                onNavigateToSettingPage = onNavigateToSettingPage,
                onNavigateToCreateTodoPage = onNavigateToCreateTodoPage
            )
            Spacer(modifier = modifier.height(8.dp))
            HomeWeatherCard(
                isLoading = homeUiState.isLoadingWeatherData,
                weatherInfoModel = homeUiState.weatherInfoModel,
                onClick = onNavigateToWeatherDetailPage
            )
            Spacer(modifier = modifier.height(8.dp))
            HomeFoodRecipesCard(
                isLoading = homeUiState.isLoadingFoodRecipesData,
                onClick = onNavigateToFoodRecipesDetailPage
            )
            Spacer(modifier = modifier.height(32.dp))
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HomePagePreview() {
    HomePage(
        homeUiState = HomeUiState(),
        onNavigateToSubscribePage = {},
        onNavigateToSettingPage = {},
        onNavigateToCreateTodoPage = {},
        onNavigateToWeatherDetailPage = {},
        onNavigateToFoodRecipesDetailPage = {}
    )
}