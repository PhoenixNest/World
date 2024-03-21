package io.dev.relic.feature.pages.studio.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.module.map.tomtom.ui.TomTomMapComponent

@Composable
fun StudioPageContent() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 100.dp)
    ) {
        TomTomMapComponent(
            onLocationUpdate = {
                //
            }
        )
    }
}

@Composable
@Preview
private fun StudioPageContentPreview() {
    StudioPageContent()
}