package io.dev.relic.feature.main.unit.home.widget.card

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.dev.relic.R
import io.dev.relic.domain.model.weather.WeatherDataModel
import io.dev.relic.domain.model.weather.WeatherInfoModel
import io.dev.relic.feature.main.unit.home.widget.HomePageCardTitle
import io.dev.relic.global.utils.TimeUtil

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeWeatherCard(
    weatherInfoModel: WeatherInfoModel?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    HomePageCardTitle(titleResId = R.string.home_card_weather_title)
    Spacer(modifier = modifier.height(8.dp))
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 2.dp
    ) {
        weatherInfoModel?.currentWeatherData?.run {

        }?.run {

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeWeatherCardPreview() {
    HomeWeatherCard(
        weatherInfoModel = WeatherInfoModel(
            weatherDataPerDay = emptyMap(),
            currentWeatherData = WeatherDataModel(
                time = TimeUtil.getCurrentTime(),
                temperature = 28.3,
                weatherCode = -1,
                humidity = 70,
                windSpeed = 201.2,
                pressure = 98.2
            )
        ),
        onClick = {}
    )
}