package io.dev.relic.feature.pages.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
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
import io.dev.relic.domain.model.weather.WeatherDataModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.model.weather.WeatherType
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.home.viewmodel.HomeState
import io.dev.relic.feature.pages.home.viewmodel.HomeViewModel
import io.dev.relic.feature.screens.main.MainState
import io.dev.relic.global.RelicConstants
import io.dev.relic.global.widget.CommonLoadingComponent
import io.dev.relic.global.widget.CommonNoDataComponent
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainBackgroundColorLight
import io.dev.relic.ui.theme.mainButtonColorLight
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainTextColorDark
import io.dev.relic.ui.theme.mainThemeColor
import io.dev.relic.ui.theme.mainThemeColorAccent
import java.time.LocalDateTime

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
        is HomeState.FetchingData -> {
            CommonLoadingComponent()
        }

        is HomeState.FetchDataFailed -> {

        }

        is HomeState.FetchDataSucceed -> {
            HomePage(
                weatherInfoModel = homeState.weatherData,
                foodRecipesDataList = homeState.recipesData,
                onRefreshWeatherData = {}
            )
        }

        else -> {}
    }
}

@Composable
private fun HomePage(
    weatherInfoModel: WeatherInfoModel?,
    foodRecipesDataList: List<FoodRecipesComplexSearchInfoModel>?,
    onRefreshWeatherData: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = rememberLazyListState(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            contentPadding = PaddingValues(
                top = 8.dp,
                bottom = 16.dp
            )
        ) {
            item {
                HomePageTopBar(onOpenDrawer = {})
                Spacer(modifier = Modifier.height(16.dp))
                WeatherCard(
                    model = weatherInfoModel?.currentWeatherData,
                    onRetryClick = onRefreshWeatherData
                )
                Spacer(modifier = Modifier.height(16.dp))
                HomePageTodoCard()
            }
            items(
                items = foodRecipesDataList ?: emptyList(),
                key = { it.id }
            ) {
                HomePageFoodRecipesCard(it)
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
private fun WeatherCard(
    model: WeatherDataModel?,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(136.dp)
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = mainBackgroundColorLight
        ) {
            if (model == null) {
                //
            } else {
                val weatherType: WeatherType = WeatherType.fromWMO(model.weatherCode)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.wrapContentHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "${model.temperature}°C",
                            style = TextStyle(
                                color = mainTextColorDark,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = RelicFontFamily.ubuntu
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = weatherType.weatherDesc,
                            style = TextStyle(
                                color = mainTextColorDark,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = RelicFontFamily.ubuntu
                            )
                        )
                    }
                    Image(
                        painter = painterResource(weatherType.iconRes),
                        contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                        modifier = Modifier
                            .height(72.dp)
                            .width(72.dp),
                        colorFilter = ColorFilter.tint(Color.DarkGray)
                    )
                }
            }
        }
        Card(
            modifier = Modifier.padding(horizontal = 36.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = mainThemeColorAccent
        ) {
            Text(
                text = stringResource(R.string.home_card_weather_title),
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = RelicFontFamily.ubuntu,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
private fun HomePageFoodRecipesCard(model: FoodRecipesComplexSearchInfoModel?) {
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
@Preview
private fun WeatherCardNoDataPreview() {
    WeatherCard(
        model = null,
        onRetryClick = {}
    )
}

@Composable
@Preview
private fun WeatherCardPreview() {
    WeatherCard(
        model = WeatherDataModel(
            time = LocalDateTime.now(),
            humidity = 10,
            temperature = 25.0,
            weatherCode = 57,
            windSpeed = 100.0,
            pressure = 120.0,
            isDay = true
        ),
        onRetryClick = {}
    )
}

@Composable
@Preview(showBackground = true)
private fun HomePagePreview() {
    HomePage(
        weatherInfoModel = null,
        foodRecipesDataList = emptyList(),
        onRefreshWeatherData = {}
    )
}