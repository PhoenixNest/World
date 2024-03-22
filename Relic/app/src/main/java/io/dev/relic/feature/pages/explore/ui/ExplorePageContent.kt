package io.dev.relic.feature.pages.explore.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.module.map.tomtom.ui.TomTomMapComponent

@Composable
fun ExplorePageContent() {
    Surface(modifier = Modifier.fillMaxSize()) {
        TomTomMapComponent()
    }
}

@Composable
@Preview
private fun ExplorePageContentPreview() {
    ExplorePageContent()
}