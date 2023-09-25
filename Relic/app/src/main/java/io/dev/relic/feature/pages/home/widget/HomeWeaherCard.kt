package io.dev.relic.feature.pages.home.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.dev.relic.R
import io.dev.relic.domain.model.weather.WeatherDataModel
import io.dev.relic.domain.model.weather.WeatherType
import io.dev.relic.feature.pages.home.viewmodel.state.HomeWeatherDataState
import io.dev.relic.global.RelicConstants
import io.dev.relic.global.widget.CommonRetryComponent
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainBackgroundColorLight
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainTextColorDark
import io.dev.relic.ui.theme.mainThemeColorAccent
import io.dev.relic.ui.theme.placeHolderHighlightColor
import java.time.LocalDateTime

@Composable
fun HomeWeatherCard(
    weatherDataState: HomeWeatherDataState,
    onRetryClick: () -> Unit
) {
    when (val state: HomeWeatherDataState = weatherDataState) {
        is HomeWeatherDataState.Init,
        is HomeWeatherDataState.Fetching -> {
            HomeWeatherCard(
                isLoading = true,
                model = null,
                onRetryClick = {}
            )
        }

        is HomeWeatherDataState.FetchSucceed -> {
            HomeWeatherCard(
                isLoading = false,
                model = state.model?.currentWeatherData,
                onRetryClick = onRetryClick
            )
        }

        is HomeWeatherDataState.Empty,
        is HomeWeatherDataState.NoWeatherData,
        is HomeWeatherDataState.FetchFailed -> {
            HomeWeatherCard(
                isLoading = false,
                model = null,
                onRetryClick = onRetryClick
            )
        }
    }
}

@Composable
private fun HomeWeatherCard(
    isLoading: Boolean,
    model: WeatherDataModel?,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(136.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .align(Alignment.BottomCenter)
                .placeholder(
                    visible = isLoading,
                    color = Color.DarkGray,
                    shape = RoundedCornerShape(16.dp),
                    highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                ),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = mainBackgroundColorLight
        ) {
            if (model == null) {
                CommonRetryComponent(
                    onRetryClick = onRetryClick,
                    containerHeight = 120.dp
                )
            } else {
                HomeWeatherCardContent(model)
            }
        }
        Card(
            modifier = Modifier.padding(horizontal = 16.dp),
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
private fun HomeWeatherCardContent(model: WeatherDataModel) {
    val weatherType: WeatherType = WeatherType.fromWMO(model.weatherCode ?: -1)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 24.dp),
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

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282c34)
private fun HomeWeatherCardNoDataPreview() {
    HomeWeatherCard(
        isLoading = false,
        model = null,
        onRetryClick = {}
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282c34)
private fun HomeWeatherCardPreview() {
    HomeWeatherCard(
        isLoading = false,
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