package io.dev.relic.feature.screens.intro

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.feature.pages.intro.IntroPage

@Composable
fun IntroScreen(onNavigateClick: () -> Unit) {
    IntroPage(onNavigateClick = onNavigateClick)
}

@Composable
@Preview(showBackground = true)
private fun IntroScreenPreview() {
    IntroScreen(onNavigateClick = {})
}