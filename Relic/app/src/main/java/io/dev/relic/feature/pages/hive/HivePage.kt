package io.dev.relic.feature.pages.hive

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.news.EverythingNewsDataState
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.pages.hive.viewmodel.HiveViewModel
import io.dev.relic.feature.screens.main.MainState

@Composable
fun HivePageRoute(
    mainViewModel: MainViewModel,
    hiveViewModel: HiveViewModel = hiltViewModel()
) {
    val mainState: MainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()
    val everythingNewsDataState: EverythingNewsDataState = hiveViewModel.everythingNewsDataStateFlow.collectAsStateWithLifecycle().value
    val topHeadlineNewsDataState: TopHeadlineNewsDataState = hiveViewModel.topHeadlineNewsDataStateFlow.collectAsStateWithLifecycle().value

    HivePage(
        everythingNewsDataState = everythingNewsDataState,
        topHeadlineNewsDataState = topHeadlineNewsDataState
    )
}

@Composable
private fun HivePage(
    everythingNewsDataState: EverythingNewsDataState,
    topHeadlineNewsDataState: TopHeadlineNewsDataState
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = mainThemeColor
    ) {
        //
    }
}