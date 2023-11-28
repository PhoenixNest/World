package io.dev.relic.feature.pages.hive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R
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
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = mainThemeColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.hive_label),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 30.sp
            )
        )
    }
}