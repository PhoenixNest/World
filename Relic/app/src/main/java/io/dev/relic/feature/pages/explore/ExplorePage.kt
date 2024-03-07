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
import com.tomtom.sdk.location.GeoLocation
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.activities.main.viewmodel.MainViewModel
import io.dev.relic.feature.pages.explore.ui.bottom_sheet.ExploreBottomSheet
import io.dev.relic.feature.pages.explore.viewmodel.ExploreViewModel
import io.dev.relic.feature.screens.main.MainScreenState
import io.module.map.amap.ui.AMapComponent

@Composable
fun ExplorePageRoute(
    mainScreenState: MainScreenState,
    mainViewModel: MainViewModel,
    exploreViewModel: ExploreViewModel = hiltViewModel()
) {
    val mainState by mainViewModel.mainStateFlow.collectAsStateWithLifecycle()
    ExplorePage(
        currentSelectedBottomSheetTab = exploreViewModel.currentSelectedBottomSheetTab,
        onTabItemClick = { currentSelectedTab, selectedItem ->
            exploreViewModel.updateSelectedBottomSheetTab(currentSelectedTab)
        },
        onLocationUpdate = { location ->
            //
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ExplorePage(
    currentSelectedBottomSheetTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onLocationUpdate: (location: GeoLocation) -> Unit
) {
    BottomSheetScaffold(
        sheetContent = {
            ExploreBottomSheet(
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
        onTabItemClick = { _, _ -> },
        onLocationUpdate = { _ -> }
    )
}