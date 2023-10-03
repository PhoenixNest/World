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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.dev.relic.R
import io.dev.relic.ui.theme.RelicFontFamily
import io.dev.relic.ui.theme.mainBackgroundColor
import io.dev.relic.ui.theme.mainBackgroundColorLight
import io.dev.relic.ui.theme.mainTextColor

@Composable
fun ExploreBottomSheet(
    bottomSheetHeight: Dp,
    currentSelectedTab: Int,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        color = mainBackgroundColorLight.copy(alpha = 0.52F),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(vertical = 16.dp)
            ) {
                ExploreBottomSheetTabBar(
                    currentSelectedTab = currentSelectedTab,
                    onTabItemClick = onTabItemClick
                )
            }
        }
    }
}

@Composable
@Preview
private fun ExploreBottomSheetPreview() {
    ExploreBottomSheet(
        bottomSheetHeight = 512.dp,
        currentSelectedTab = 0,
        onTabItemClick = { _: Int, _: String -> }
    )
}
