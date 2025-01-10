package io.dev.relic.feature.screens.intro

import androidx.compose.runtime.Composable
import io.dev.relic.feature.pages.intro.IntroPage

@Composable
fun IntroScreen(
    isCompatMode: Boolean,
    onNavigateClick: () -> Unit
) {
    IntroPage(
        isCompatMode = isCompatMode,
        onNavigateClick = onNavigateClick
    )
}