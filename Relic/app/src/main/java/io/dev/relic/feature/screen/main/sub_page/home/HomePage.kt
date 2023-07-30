package io.dev.relic.feature.screen.main.sub_page.home

import android.Manifest
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import io.dev.relic.feature.screen.main.sub_page.home.viewmodel.HomeUiState
import io.dev.relic.feature.screen.main.sub_page.home.viewmodel.HomeViewModel
import io.dev.relic.feature.screen.main.sub_page.home.widget.card.HomeFoodRecipesCard
import io.dev.relic.feature.screen.main.sub_page.home.widget.card.HomeWeatherCard

internal const val TAG = "HomePage"

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomePageRoute(
    onNavigateToSubscribePage: () -> Unit,
    onNavigateToSettingPage: () -> Unit,
    onNavigateToWeatherDetailPage: () -> Unit,
    onNavigateToFoodRecipesDetailPage: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val multiplePermissionsState: MultiplePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    LaunchedEffect(key1 = multiplePermissionsState.allPermissionsGranted) {
        if (multiplePermissionsState.allPermissionsGranted) {
            viewModel.accessDeviceLocation()
        }
    }

    HomePage(
        state = viewModel.state,
        onNavigateToSubscribePage = onNavigateToSubscribePage,
        onNavigateToSettingPage = onNavigateToSettingPage,
        onNavigateToWeatherDetailPage = onNavigateToWeatherDetailPage,
        onNavigateToFoodRecipesDetailPage = onNavigateToFoodRecipesDetailPage
    )
}

@Composable
private fun HomePage(
    state: HomeUiState,
    onNavigateToSubscribePage: () -> Unit,
    onNavigateToSettingPage: () -> Unit,
    onNavigateToWeatherDetailPage: () -> Unit,
    onNavigateToFoodRecipesDetailPage: () -> Unit
) {
    HomePageContent(
        state = state,
        onNavigateToSubscribePage = onNavigateToSubscribePage,
        onNavigateToSettingPage = onNavigateToSettingPage,
        onNavigateToWeatherDetailPage = onNavigateToWeatherDetailPage,
        onNavigateToFoodRecipesDetailPage = onNavigateToFoodRecipesDetailPage
    )
}

@Composable
private fun HomePageContent(
    state: HomeUiState,
    onNavigateToSubscribePage: () -> Unit,
    onNavigateToSettingPage: () -> Unit,
    onNavigateToWeatherDetailPage: () -> Unit,
    onNavigateToFoodRecipesDetailPage: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues: PaddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(
                    state = rememberScrollState(),
                    enabled = true
                )
                .background(color = Color.LightGray.copy(alpha = 0.2F)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            HomeWeatherCard(
                isLoading = state.isLoadingWeatherData,
                weatherInfoModel = state.weatherData,
                onClick = onNavigateToWeatherDetailPage
            )
            Spacer(modifier = Modifier.height(8.dp))
            HomeFoodRecipesCard(
                isLoading = state.isLoadingFoodRecipesData,
                foodRecipesInfoModelList = state.foodRecipesDataList,
                onClick = onNavigateToFoodRecipesDetailPage
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun HomePagePreview() {
    HomePage(
        state = HomeUiState(),
        onNavigateToSubscribePage = {},
        onNavigateToSettingPage = {},
        onNavigateToWeatherDetailPage = {},
        onNavigateToFoodRecipesDetailPage = {}
    )
}