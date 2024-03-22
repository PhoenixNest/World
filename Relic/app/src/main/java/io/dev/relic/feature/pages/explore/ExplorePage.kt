package io.dev.relic.feature.pages.explore

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.screens.main.MainScreenState
import io.module.map.tomtom.ui.TomTomMapComponent

@Composable
fun ExplorePageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel
) {
    ExplorePage()
}

@Composable
private fun ExplorePage() {
    Surface(modifier = Modifier.fillMaxSize()) {
        TomTomMapComponent()
    }
}

@Composable
@Preview
private fun ExplorePagePreview() {
    ExplorePage()
}