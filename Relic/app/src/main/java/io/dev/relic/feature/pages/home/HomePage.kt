package io.dev.relic.feature.pages.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.R
import io.dev.relic.domain.model.food_recipes.FoodRecipesComplexSearchInfoModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.home.viewmodel.HomeState
import io.dev.relic.feature.pages.home.viewmodel.HomeViewModel
import io.dev.relic.feature.screens.main.MainState
import io.dev.relic.global.RelicConstants
import io.dev.relic.global.widget.CommonLoadingComponent
import io.dev.relic.global.widget.CommonNoDataComponent
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainButtonColorLight
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun HomePageRoute(
    mainViewModel: MainViewModel,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val mainState: MainState = mainViewModel.mainStateFlow.collectAsStateWithLifecycle().value
    val homeState: HomeState = homeViewModel.homeState.collectAsStateWithLifecycle().value

    when (mainState) {
        is MainState.Init -> {
            //
        }

        is MainState.Empty -> {
            CommonNoDataComponent()
        }

        is MainState.AccessingLocation -> {
            CommonLoadingComponent()
        }

        is MainState.AccessLocationFailed -> {
            CommonNoDataComponent()
        }

        is MainState.AccessLocationSucceed -> {
            mainState.location?.run {
                homeViewModel.exec(
                    latitude = this.latitude,
                    longitude = this.longitude,
                    offset = 0
                )
            }
            HomePage(homeState)
        }
    }
}

@Composable
private fun HomePage(homeState: HomeState) {
    when (homeState) {
        is HomeState.Init -> {

        }

        is HomeState.Empty -> {

        }

        is HomeState.NoFoodRecipesData -> {

        }

        is HomeState.NoWeatherData -> {

        }

        is HomeState.FetchingData -> {

        }

        is HomeState.FetchWeatherDataSucceed -> {

        }

        is HomeState.FetchFoodRecipesDataSucceed -> {

        }

        is HomeState.FetchWeatherDataFailed -> {

        }

        is HomeState.FetchFoodRecipesDataFailed -> {

        }
    }
}

@Composable
private fun HomePage(
    weatherInfoModel: WeatherInfoModel?,
    foodRecipesDataList: List<FoodRecipesComplexSearchInfoModel>?
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                HomePageTopBar(onOpenDrawer = {})
                Spacer(modifier = Modifier.height(16.dp))
                HomePageWeatherCard(weatherInfoModel = weatherInfoModel)
                Spacer(modifier = Modifier.height(16.dp))
                HomePageTradeCard()
                Spacer(modifier = Modifier.height(16.dp))
                HomePageTodoCard()
                Spacer(modifier = Modifier.height(16.dp))
            }

            items(
                items = foodRecipesDataList ?: emptyList(),
                key = { it.id }
            ) {
                HomePageFoodRecipesCard()
            }
        }
    }
}

@Composable
private fun HomePageTopBar(onOpenDrawer: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        IconButton(
            onClick = onOpenDrawer,
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Rounded.Apps,
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = mainButtonColorLight
            )
        }
        Text(
            text = stringResource(R.string.home_main_title),
            modifier = Modifier.align(Alignment.Center),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RelicFontFamily.ubuntu,
                textMotion = TextMotion.Animated
            )
        )
    }
}

@Composable
private fun HomePageTradeCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        //
    }
}

@Composable
private fun HomePageWeatherCard(weatherInfoModel: WeatherInfoModel?) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        //
    }
}

@Composable
private fun HomePageFoodRecipesCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        //
    }
}

@Composable
private fun HomePageTodoCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        backgroundColor = Color.LightGray
    ) {
        //
    }
}

@Composable
@Preview(showBackground = true)
private fun HomePagePreview() {
    HomePage(
        weatherInfoModel = null,
        foodRecipesDataList = emptyList()
    )
}