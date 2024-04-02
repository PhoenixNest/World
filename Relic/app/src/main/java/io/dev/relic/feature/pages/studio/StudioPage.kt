package io.dev.relic.feature.pages.studio

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.function.news.viewmodel.NewsViewModel
import io.dev.relic.feature.pages.studio.ui.StudioPageContent
import io.dev.relic.feature.screens.main.MainScreenState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StudioPageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    newsViewModel: NewsViewModel
) {
    BottomSheetScaffold(
        sheetContent = {
            StudioPageBottomSheet(
                newsViewModel = newsViewModel,
                mainScreenState = mainScreenState
            )
        },
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetBackgroundColor = mainThemeColor,
        sheetPeekHeight = 140.dp,
    ) {
        StudioPageContent()
    }
}