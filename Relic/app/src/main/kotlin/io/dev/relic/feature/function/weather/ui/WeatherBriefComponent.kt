package io.dev.relic.feature.function.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import io.common.RelicConstants
import io.core.ui.CommonRetryComponent
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColor30
import io.core.ui.theme.mainTextColor80
import io.core.ui.theme.mainThemeColor
import io.core.ui.theme.placeHolderHighlightColor
import io.data.model.weather.WeatherDataModel
import io.data.model.weather.WeatherType
import io.dev.relic.feature.function.weather.WeatherDataState
import java.time.LocalDateTime

@Composable
fun WeatherBriefComponent(
    weatherDataState: WeatherDataState,
    onRetryClick: () -> Unit,
    isGrayWeatherIcon: Boolean = false
) {
    when (weatherDataState) {
        is WeatherDataState.Init,
        is WeatherDataState.Fetching -> {
            WeatherBriefPlaceholder()
        }

        is WeatherDataState.FetchSucceed -> {
            weatherDataState.model?.currentWeatherData?.also {
                WeatherBriefContent(
                    model = it,
                    isGrayWeatherIcon = isGrayWeatherIcon
                )
            }
        }

        is WeatherDataState.Empty,
        is WeatherDataState.NoWeatherData,
        is WeatherDataState.FetchFailed -> {
            WeatherBriefRetry(onRetryClick = onRetryClick)
        }
    }
}

@Composable
@Preview
private fun WeatherBriefPlaceholder() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Transparent
        ) {
            Box(
                modifier = Modifier
                    .width(128.dp)
                    .height(120.dp)
                    .placeholder(
                        visible = true,
                        color = Color.LightGray.copy(alpha = 0.3F),
                        shape = RoundedCornerShape(16.dp),
                        highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                    )
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Top
            )
        ) {
            repeat(3) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(32.dp),
                    shape = RoundedCornerShape(8.dp),
                    color = Color.Transparent
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .placeholder(
                                visible = true,
                                color = Color.LightGray.copy(0.3F),
                                shape = RoundedCornerShape(8.dp),
                                highlight = PlaceholderHighlight.shimmer(highlightColor = placeHolderHighlightColor)
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun WeatherBriefContent(
    model: WeatherDataModel?,
    isGrayWeatherIcon: Boolean
) {
    val temperature = model?.temperature ?: 0.0
    val weatherType = WeatherType.fromWMO(model?.weatherCode ?: -1)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${temperature.toInt()}°C",
            style = TextStyle(
                color = mainTextColor,
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
        Column(
            modifier = Modifier.wrapContentSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(weatherType.iconRes),
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                modifier = Modifier
                    .height(72.dp)
                    .width(72.dp),
                colorFilter = if (isGrayWeatherIcon) {
                    ColorFilter.tint(mainTextColor80)
                } else null
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = weatherType.weatherDesc,
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 16.sp,
                    fontFamily = RelicFontFamily.ubuntu
                )
            )
        }
    }
}

@Composable
private fun WeatherBriefRetry(onRetryClick: () -> Unit) {
    CommonRetryComponent(
        onRetryClick = onRetryClick,
        containerHeight = 96.dp,
        backgroundColor = mainThemeColor.copy(alpha = 0.7F)
    )
}

@Composable
@Preview
private fun WeatherBriefContentPreview() {
    WeatherBriefContent(
        model = WeatherDataModel(
            time = LocalDateTime.now(),
            temperature = 24.0,
            humidity = 24,
            weatherCode = -1,
            pressure = 50.0,
            windSpeed = 24.0,
            isDay = false
        ),
        isGrayWeatherIcon = false
    )
}

@Composable
@Preview
private fun WeatherBriefRetryPreview() {
    WeatherBriefRetry(onRetryClick = {})
}