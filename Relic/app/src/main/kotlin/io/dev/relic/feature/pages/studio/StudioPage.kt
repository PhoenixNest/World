package io.dev.relic.feature.pages.studio

import android.os.CpuUsageInfo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.common.system.BatteryUtil
import io.common.system.CpuUtil
import io.common.system.NetworkUtil
import io.dev.relic.feature.pages.studio.ui.StudioPageContent

data class SystemInfoModel(
    val batteryTemperature: Int,
    val isCharging: Boolean,
    val cpuTemperature: Float?,
    val cpuUsageInfoList: List<CpuUsageInfo?>?,
    val cpuFanSpeedsList: List<Float?>?,
    val networkType: NetworkUtil.NetworkType,
    val macAddress: String
)

@Composable
fun StudioPageRoute() {

    val context = LocalContext.current

    /* ======================== Battery ======================== */

    val batteryTemperature by BatteryUtil.getTemperatureFlow()
        .collectAsStateWithLifecycle()

    val isCharging by BatteryUtil.getChargingFlow()
        .collectAsStateWithLifecycle()

    /* ======================== Cpu ======================== */

    val cpuTemperature by CpuUtil.getCpuTemperatureFlow()
        .collectAsStateWithLifecycle()

    val cpuUsageInfoList by CpuUtil.getCpuUsageInfoFlow()
        .collectAsStateWithLifecycle()

    val cpuFanSpeedsList by CpuUtil.getFanSpeedsFlow()
        .collectAsStateWithLifecycle()

    /* ======================== Network ======================== */

    val networkType = NetworkUtil.getCurrentNetworkType(context)

    val macAddressInfo = NetworkUtil.getMacAddressInfo(context)

    StudioPage(
        SystemInfoModel(
            batteryTemperature = batteryTemperature,
            isCharging = isCharging,
            cpuTemperature = cpuTemperature,
            cpuUsageInfoList = cpuUsageInfoList,
            cpuFanSpeedsList = cpuFanSpeedsList,
            networkType = networkType,
            macAddress = macAddressInfo
        )
    )
}

@Composable
private fun StudioPage(systemInfoModel: SystemInfoModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        StudioPageContent(systemInfoModel)
    }
}