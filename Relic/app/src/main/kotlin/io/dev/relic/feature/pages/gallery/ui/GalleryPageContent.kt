package io.dev.relic.feature.pages.gallery.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev.relic.feature.function.gallery.widget.GalleryStaggeredGrid
import io.dev.relic.feature.function.gallery.widget.GalleryStaggeredGridAction

@Composable
fun GalleryPageContent(
    galleryStaggeredGridAction: GalleryStaggeredGridAction
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GalleryStaggeredGrid(action = galleryStaggeredGridAction)
    }
}