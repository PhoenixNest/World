package io.dev.relic.feature.pages.explore.widget.bottom_sheet

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.ui.theme.RelicFontFamily
import io.ui.theme.mainBackgroundColor
import io.ui.theme.mainBackgroundColorLight
import io.ui.theme.mainTextColor

@Composable
fun ExploreBottomSheet(
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    val screenHeight: Dp = LocalConfiguration.current.screenHeightDp.dp
    val bottomSheetHeight: Dp = screenHeight - 52.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(bottomSheetHeight),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp
        ),
        backgroundColor = mainBackgroundColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExploreBottomSheetHeader()
            ExploreBottomSheetContent(
                currentSelectedTab = currentSelectedTab,
                onTabItemClick = onTabItemClick
            )
        }
    }
}

@Composable
private fun ExploreBottomSheetHeader() {
    Column(
        modifier = Modifier.wrapContentSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .height(4.dp)
                    .width(96.dp)
                    .align(Alignment.Center)
                    .background(
                        color = mainBackgroundColorLight,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(bottom = 96.dp)
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = stringResource(id = R.string.explore_bottom_sheet_title),
            style = TextStyle(
                color = mainTextColor,
                fontSize = 18.sp,
                fontFamily = RelicFontFamily.ubuntu
            )
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun ExploreBottomSheetContent(
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = mainBackgroundColorLight.copy(alpha = 0.52F),
                shape = RoundedCornerShape(
                    topStart = 16.dp,
                    topEnd = 16.dp
                )
            )
            .padding(vertical = 16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ExploreBottomSheetTabBar(
                currentSelectedTab = currentSelectedTab,
                onTabItemClick = onTabItemClick
            )
        }
    }
}

@Composable
@Preview
private fun ExploreBottomSheetPreview() {
    ExploreBottomSheet(
        currentSelectedTab = 0,
        onTabItemClick = { _: Int, _: String -> }
    )
}
