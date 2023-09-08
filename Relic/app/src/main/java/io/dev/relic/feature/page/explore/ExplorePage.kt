package io.dev.relic.feature.page.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.domain.map.amap.AMapConfig
import io.dev.relic.domain.map.amap.AMapComponent
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.screen.main.MainState
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun ExplorePageRoute(mainViewModel: MainViewModel) {
    val mainState: MainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()

    ExplorePage()
}

@Composable
private fun ExplorePage() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor)
    ) {
        AMapComponent(
            aMapOptionsFactory = AMapConfig.OptionsConfig::defaultConfig,
            locationStyleFactory = AMapConfig.MapConfig::defaultConfig
        )
    }
}