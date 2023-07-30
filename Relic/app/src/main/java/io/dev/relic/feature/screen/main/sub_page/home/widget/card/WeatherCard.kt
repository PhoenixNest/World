package io.dev.relic.feature.screen.main.sub_page.home.widget.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import io.dev.relic.R
import io.dev.relic.domain.model.weather.WeatherDataModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.domain.model.weather.WeatherType
import io.dev.relic.global.RelicConstants.ComposeUi.DEFAULT_DESC
import io.dev.relic.global.utils.TimeUtil
import io.dev.relic.global.widget.CommonCardTitle
import io.dev.relic.ui.theme.RelicFontFamily

@Composable
fun HomeWeatherCard(
    isLoading: Boolean,
    weatherInfoModel: WeatherInfoModel?,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CommonCardTitle(titleResId = R.string.home_card_weather_title)
        Spacer(modifier = Modifier.height(8.dp))
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(horizontal = 16.dp)
                .placeholder(
                    visible = isLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                    shape = RoundedCornerShape(16.dp)
                ),
            shape = RoundedCornerShape(16.dp)
        ) {
            weatherInfoModel?.currentWeatherData?.run {
                HomeWeatherCardContent(
                    dataModel = this,
                    onClick = onClick
                )
            } ?: run {
                // Text(text = "No Weather Data")
            }
        }
    }
}

@Composable
private fun HomeWeatherCardContent(
    dataModel: WeatherDataModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(
                        Color.DarkGray.copy(alpha = 0.8F),
                        Color.DarkGray
                    )
                )
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeWeatherCardLeftPanel(dataModel = dataModel)
        Spacer(modifier = modifier.width(8.dp))
        HomeWeatherCardRightPanel(
            dataModel = dataModel,
            onClick = onClick,
        )
    }
}

@Composable
private fun RowScope.HomeWeatherCardLeftPanel(dataModel: WeatherDataModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .weight(2F),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = dataModel.time.toString(),
            style = TextStyle(
                color = Color.LightGray,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = dataModel.temperature.toString(),
            style = TextStyle(
                color = Color.White,
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
    }
}

@Composable
private fun RowScope.HomeWeatherCardRightPanel(
    dataModel: WeatherDataModel,
    onClick: () -> Unit
) {
    val weatherIconRes: Int = WeatherType.fromWMO(
        dataModel.weatherCode
    ).iconRes

    Column(
        modifier = Modifier
            .fillMaxSize()
            .weight(1F),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = weatherIconRes),
            contentDescription = DEFAULT_DESC,
            modifier = Modifier.size(120.dp),
            contentScale = ContentScale.Inside
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White
            )
        ) {
            Text(
                text = "View Detail",
                style = TextStyle(
                    color = Color.DarkGray,
                    fontSize = 12.sp,
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeWeatherCardPreview() {
    HomeWeatherCard(
        isLoading = false,
        weatherInfoModel = WeatherInfoModel(
            weatherDataPerDay = emptyMap(),
            currentWeatherData = WeatherDataModel(
                time = TimeUtil.getCurrentTime(),
                temperature = 28.3,
                weatherCode = -1,
                humidity = 70,
                windSpeed = 201.2,
                pressure = 98.2,
                isDay = true
            )
        ),
        onClick = {}
    )
}