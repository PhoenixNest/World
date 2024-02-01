package io.dev.relic.feature.screens.main.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R
import io.dev.relic.feature.function.wallpaper.LocalWallpaperCover
import io.dev.relic.feature.function.weather.WeatherDataState
import io.dev.relic.feature.function.weather.ui.WeatherDrawerPanel

@Composable
fun MainDrawer(
    weatherDataState: WeatherDataState,
    onWeatherRetry: () -> Unit
) {
    // val context = LocalContext.current
    // val osInfo = SystemUtil.getOSInfo()
    // val phoneBoardInfo = SystemUtil.getPhoneBoardInfo()
    // val phoneModelInfo = SystemUtil.getPhoneModelInfo()
    // val availableProcessors = SystemUtil.getAvailableProcessors()
    // val totalROMSize = MemoryUtil.getTotalROMSize()
    // val freeRAMSize = MemoryUtil.getFreeRAMSize(context)
    // val currentChargingStatus by getChargingFlow().collectAsStateWithLifecycle()
    // val currentBatteryTemperature by getTemperatureFlow().collectAsStateWithLifecycle()
    // val screenInch = ScreenUtil.getScreenInch(context)
    // val phoneScreenWidth = ScreenUtil.getScreenWidth(context)
    // val phoneScreenHeight = ScreenUtil.getScreenHeight(context)
    // val currentNetworkType = NetworkUtil.getCurrentNetworkType(context).name
    // val macAddressInfo = NetworkUtil.getMacAddressInfo(context)
    // val gateWay = NetworkUtil.getGateWay()

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .fillMaxHeight()
    ) {
        MainDrawerCover()
        MainDrawerDarkLayer()
        MainDrawerBriefPanel(weatherDataState, onWeatherRetry)
        MainDrawerMaximPanel()
    }
}

@Composable
private fun MainDrawerCover() {
    LocalWallpaperCover(resId = R.mipmap.home)
}

@Composable
private fun MainDrawerDarkLayer() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor.copy(alpha = 0.6F))
    )
}

@Composable
private fun BoxScope.MainDrawerBriefPanel(
    weatherDataState: WeatherDataState,
    onWeatherRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .align(Alignment.TopStart)
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 52.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "12 July'18",
            fontFamily = ubuntu,
            style = TextStyle(
                color = mainTextColor,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .width(72.dp)
                .height(2.dp)
                .background(color = mainTextColor)
        )
        Spacer(modifier = Modifier.height(20.dp))
        WeatherDrawerPanel(
            weatherDataState = weatherDataState,
            onRetryClick = onWeatherRetry
        )
    }
}

@Composable
private fun BoxScope.MainDrawerMaximPanel() {
    Column(
        modifier = Modifier
            .align(Alignment.BottomStart)
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "EARTH",
            style = TextStyle(
                color = mainTextColor.copy(alpha = 0.5F),
                fontSize = 16.sp,
                fontFamily = ubuntu
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "WE'RE \nIN THE MIDDLE \nOF NOWHERE.",
            style = TextStyle(
                color = mainTextColor,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = ubuntu
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = "I'd love to tell you that the sun will come out tomorrow, but it might not. I'm no miracle worker.",
            style = TextStyle(
                color = mainTextColor.copy(alpha = 0.5F),
                fontSize = 16.sp,
                fontFamily = ubuntu,
                lineHeight = TextUnit(
                    value = 1.6F,
                    type = TextUnitType.Em
                )
            )
        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
@Preview(showBackground = true)
private fun MainDrawerPreview() {
    MainDrawer(
        weatherDataState = WeatherDataState.Empty,
        onWeatherRetry = {}
    )
}
