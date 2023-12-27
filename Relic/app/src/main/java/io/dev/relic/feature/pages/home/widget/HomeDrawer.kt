package io.dev.relic.feature.pages.home.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.system.BatteryUtil
import io.common.system.MemoryUtil
import io.common.system.NetworkUtil
import io.common.system.ScreenUtil
import io.common.system.SystemUtil
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainTextColor

@Composable
fun HomeDrawer() {

    val context = LocalContext.current
    val screenWidth: Dp = LocalConfiguration.current.screenWidthDp.dp
    val drawerWidth: Dp = screenWidth - 72.dp

    val currentChargingStatus: Boolean =
        BatteryUtil.getChargingFlow().collectAsStateWithLifecycle().value
    val currentBatteryTemperature: Int =
        BatteryUtil.getTemperatureFlow().collectAsStateWithLifecycle().value

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .width(drawerWidth)
            .fillMaxHeight()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "[MemoryUtil getOSInfo] ${SystemUtil.getOSInfo()}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[SystemUtil getPhoneBoardInfo] ${SystemUtil.getPhoneBoardInfo()}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[SystemUtil getPhoneModelInfo] ${SystemUtil.getPhoneModelInfo()}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[SystemUtil getAvailableProcessors] ${SystemUtil.getAvailableProcessors()}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[MemoryUtil getFreeROMSize] ${MemoryUtil.getTotalROMSize()}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[MemoryUtil getFreeRAMSize] ${MemoryUtil.getFreeRAMSize(context)}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[Charging Status] $currentChargingStatus",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[Temperature] $currentBatteryTemperature",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[getScreenInch] ${ScreenUtil.getScreenInch(context)}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[getScreenWidth] ${ScreenUtil.getScreenWidth(context)}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[getScreenHeight] ${ScreenUtil.getScreenHeight(context)}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[getCurrentNetworkType] ${NetworkUtil.getCurrentNetworkType(context).name}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[getMacAddressInfo] ${NetworkUtil.getMacAddressInfo(context)}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )

        Text(
            text = "[getGateWay] ${NetworkUtil.getGateWay()}",
            style = TextStyle(
                color = mainTextColor,
                fontFamily = ubuntu
            )
        )
    }
}