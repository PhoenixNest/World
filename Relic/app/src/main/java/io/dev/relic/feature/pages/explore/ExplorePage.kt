package io.dev.relic.feature.pages.explore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.dev.relic.domain.map.amap.ui.AMapComponent
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.explore.viewmodel.ExploreViewModel
import io.dev.relic.feature.pages.explore.widget.bottom_sheet.ExploreBottomSheet
import io.dev.relic.feature.screens.main.MainState
import io.dev.relic.ui.theme.mainThemeColor

@Composable
fun ExplorePageRoute(
    mainViewModel: MainViewModel,
    exploreViewModel: ExploreViewModel = hiltViewModel()
) {
    val mainState: MainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()
    ExplorePage(
        currentSelectedBottomSheetTab = exploreViewModel.currentSelectedBottomSheetTab,
        onTabItemClick = { currentSelectedTab: Int, selectedItem: String ->
            exploreViewModel.updateSelectedBottomSheetTab(currentSelectedTab)
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExplorePage(
    currentSelectedBottomSheetTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    BottomSheetScaffold(
        sheetContent = {
            ExploreBottomSheet(
                bottomSheetHeight = 512.dp,
                currentSelectedTab = currentSelectedBottomSheetTab,
                onTabItemClick = onTabItemClick
            )
        },
        scaffoldState = rememberBottomSheetScaffoldState(),
        sheetShape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        sheetBackgroundColor = mainThemeColor,
        sheetPeekHeight = 160.dp,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = mainThemeColor)
            ) {
                AMapComponent()
            }
        }
    )
}

@Composable
@Preview
private fun ExplorePagePreview() {
    ExplorePage(
        currentSelectedBottomSheetTab = 0,
        onTabItemClick = { _: Int, _: String -> }
    )
}