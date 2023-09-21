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
import io.dev.relic.R
import io.dev.relic.domain.model.weather.WeatherDataModel
import io.dev.relic.domain.model.weather.WeatherType
import io.dev.relic.global.RelicConstants
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainBackgroundColorLight
import io.dev.relic.ui.theme.mainTextColor
import io.dev.relic.ui.theme.mainTextColorDark
import io.dev.relic.ui.theme.mainThemeColorAccent
import java.time.LocalDateTime

@Composable
fun HomeWeatherCard(
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
@Preview
private fun HomeWeatherCardNoDataPreview() {
    HomeWeatherCard(
        model = null,
        onRetryClick = {}
    )
}

@Composable
@Preview
private fun HomeWeatherCardPreview() {
    HomeWeatherCard(
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