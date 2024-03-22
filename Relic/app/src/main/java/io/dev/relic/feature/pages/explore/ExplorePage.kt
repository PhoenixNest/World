package io.dev.relic.feature.pages.explore

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.explore.ui.ExplorePageContent
import io.dev.relic.feature.screens.main.MainScreenState

@Composable
fun ExplorePageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel
) {
    ExplorePage()
}

@Composable
private fun ExplorePage() {
    ExplorePageContent()
}

@Composable
@Preview
private fun ExplorePagePreview() {
    ExplorePage()
}