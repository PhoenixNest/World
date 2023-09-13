package io.dev.relic.feature.pages.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.R
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.screens.main.MainState
import io.dev.relic.ui.theme.mainTextColor
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
        Text(
            text = stringResource(R.string.explore_title),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 30.sp
            )
        )
    }
}