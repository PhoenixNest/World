package io.dev.relic.feature.function.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.utils.RelicUiUtil.getCurrentScreenWidthDp

@Composable
fun SystemMonitorComponent() {
    val screenWidthDp = getCurrentScreenWidthDp()
    Surface(modifier = Modifier.fillMaxSize()) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            verticalItemSpacing = 8.dp,
            horizontalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.CenterHorizontally
            )
        ) {
            item { BatteryInfoCard() }
            item { CameraInfoCard() }
            item { HealthInfoCard() }
            item { MemoryInfoCard() }
            item { NetworkInfoCard() }
            item { ScreenInfoCard() }
            item { SensorInfoCard() }
            item { SystemInfoCard() }
        }
    }
}

@Composable
private fun BatteryInfoCard() {
    // val context = LocalContext.current
    // val isCharging = BatteryUtil.isCharging(context)
    // val chargeType = BatteryUtil.getBatteryChargeType(context)
    // val remainChargeTime = BatteryUtil.getRemainChargeTime(context)
    // val temperature by BatteryUtil.getTemperatureFlow().collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .size(120.dp)
            .background(color = Color.LightGray)
    )
}

@Composable
private fun CameraInfoCard() {
    // val context = LocalContext.current
    // val cameraInfo = CameraUtil.getCameraInfo(context)

    Box(
        modifier = Modifier
            .size(140.dp)
            .background(color = Color.LightGray)
    )
}

@Composable
private fun HealthInfoCard() {
    // val context = LocalContext.current
    // val healthStats = HealthUtil.getSystemHealthStats(context)

    Box(
        modifier = Modifier
            .size(180.dp)
            .background(color = Color.LightGray)
    )
}

@Composable
private fun MemoryInfoCard() {
    // val totalRAMSize = MemoryUtil.getTotalRAMSize()
    // val totalROMSize = MemoryUtil.getTotalROMSize()

    Box(
        modifier = Modifier
            .size(160.dp)
            .background(color = Color.LightGray)
    )
}

@Composable
private fun NetworkInfoCard() {
    // val context = LocalContext.current
    // val networkType = NetworkUtil.getCurrentNetworkType(context)
    // val macAddressInfo = NetworkUtil.getMacAddressInfo(context)
    // val gateWay = NetworkUtil.getGateWay()

    Box(
        modifier = Modifier
            .size(120.dp)
            .background(color = Color.LightGray)
    )
}

@Composable
private fun ScreenInfoCard() {
    // val context = LocalContext.current
    // val screenWidth = ScreenUtil.getScreenWidth(context)
    // val screenHeight = ScreenUtil.getScreenHeight(context)
    // val screenInch = ScreenUtil.getScreenInch(context)

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color = Color.LightGray)
    )
}

@Composable
private fun SensorInfoCard() {
    // val context = LocalContext.current
    // val sensorList = SensorUtil.getAllSensors(context)

    Box(
        modifier = Modifier
            .size(100.dp)
            .background(color = Color.LightGray)
    )
}

@Composable
private fun SystemInfoCard() {
    // val osInfo = SystemUtil.getOSInfo()
    // val phoneModelInfo = SystemUtil.getPhoneModelInfo()
    // val availableProcessors = SystemUtil.getAvailableProcessors()

    Box(
        modifier = Modifier
            .size(180.dp)
            .background(color = Color.LightGray)
    )
}

@Composable
@Preview
private fun SystemMonitorComponentPreview() {
    SystemMonitorComponent()
}