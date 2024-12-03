package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.dev.relic.feature.pages.studio.SystemInfoModel
import io.dev.relic.feature.pages.studio.ui.widget.StudioWorkFlowPager
import io.dev.relic.feature.pages.studio.util.StudioWorkFlowType

@Composable
fun StudioPageContent(systemInfoModel: SystemInfoModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        /*systemInfoModel.let {
            Text(text = "Battery Temperature: ${it.batteryTemperature}")
            Text(text = "Battery isCharging: ${it.isCharging}")
            Text(text = "Cpu Temperature: ${it.cpuTemperature}")
            Text(text = "Cpu Usage info list: ${it.cpuUsageInfoList}")
            Text(text = "Cpu Fan speed: ${it.cpuFanSpeedsList}")
            Text(text = "Network type: ${it.networkType}")
            Text(text = "Network Mac address: ${it.macAddress}")
        }*/
        StudioWorkFlowPager(
            pagerState = rememberPagerState { StudioWorkFlowType.entries.size },
            workFlowList = StudioWorkFlowType.entries.toList(),
            onItemClick = {}
        )
    }
}