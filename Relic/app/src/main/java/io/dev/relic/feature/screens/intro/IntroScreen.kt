package io.dev.relic.feature.screens.intro

import androidx.compose.runtime.Composable
import io.dev.relic.feature.pages.intro.IntroPage
import io.dev.relic.feature.pages.intro.IntroPageLarge

@Composable
fun IntroScreen(
    isLargeScreen: Boolean,
    onNavigateClick: () -> Unit
) {
    if (isLargeScreen) {
        IntroPageLarge(onNavigateClick = onNavigateClick)
    } else {
        IntroPage(onNavigateClick = onNavigateClick)
    }
}