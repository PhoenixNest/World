package io.dev.relic.feature.pages.gallery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.function.gallery.GalleryDataState
import io.dev.relic.feature.function.gallery.widget.GalleryStaggeredGrid
import io.dev.relic.feature.pages.gallery.GalleryAction
import io.dev.relic.feature.pages.gallery.GalleryListState
import io.dev.relic.feature.pages.gallery.GalleryState

@Composable
fun GalleryPageContent(
    galleryState: GalleryState,
    onBackClick: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            GalleryStaggeredGrid(
                galleryDataState = galleryState.dataState,
                lazyStaggeredGridState = galleryState.listState.stagedGridState
            )
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .wrapContentSize()
                    .align(Alignment.TopStart)
                    .padding(
                        start = 16.dp,
                        top = 32.dp
                    )
                    .background(
                        color = mainThemeColor.copy(alpha = 0.3F),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = DEFAULT_DESC,
                    tint = mainIconColorLight
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun GalleryPageContentPreview() {
    GalleryPageContent(
        GalleryState(
            dataState = GalleryDataState.Init,
            action = GalleryAction(
                onItemClick = {},
                onRetryClick = {}
            ),
            listState = GalleryListState(stagedGridState = rememberLazyStaggeredGridState())
        ),
        onBackClick = {}
    )
}