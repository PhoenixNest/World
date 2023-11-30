package io.dev.relic.feature.function.weather.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import io.core.ui.CommonRetryComponent
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainBackgroundColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColorAccent
import io.core.ui.theme.placeHolderHighlightColor
import io.data.model.weather.WeatherDataModel
import io.dev.relic.R
import io.dev.relic.feature.function.weather.WeatherDataState
import java.time.LocalDateTime

@Composable
fun WeatherCard(
    weatherDataState: WeatherDataState,
    onRetryClick: () -> Unit
) {
    when (val state: WeatherDataState = weatherDataState) {
        is WeatherDataState.Init,
        is WeatherDataState.Fetching -> {
            WeatherCard(
                isLoading = true,
                model = null,
                onRetryClick = {}
            )
        }

        is WeatherDataState.FetchSucceed -> {
            WeatherCard(
                isLoading = false,
                model = state.model?.currentWeatherData,
                onRetryClick = onRetryClick
            )
        }

        is WeatherDataState.Empty,
        is WeatherDataState.NoWeatherData,
        is WeatherDataState.FetchFailed -> {
            WeatherCard(
                isLoading = false,
                model = null,
                onRetryClick = onRetryClick
            )
        }
    }
}

@Composable
private fun WeatherCard(
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
                WeatherCardContent(model)
            }
        }
        Card(
            modifier = Modifier.padding(horizontal = 16.dp),
            shape = RoundedCornerShape(8.dp),
            backgroundColor = mainThemeColorAccent
        ) {
            Text(
                text = stringResource(R.string.weather_card_title),
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
@Preview(showBackground = true, backgroundColor = 0xFF282c34)
private fun WeatherCardNoDataPreview() {
    WeatherCard(
        isLoading = false,
        model = null,
        onRetryClick = {}
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282c34)
private fun WeatherCardPreview() {
    WeatherCard(
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