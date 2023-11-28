package io.dev.relic.feature.function.weather.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColorDark
import io.data.model.weather.WeatherDataModel
import io.data.model.weather.WeatherType

@Composable
fun WeatherCardContent(model: WeatherDataModel) {
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