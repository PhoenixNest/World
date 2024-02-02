package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Apps
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainButtonColorLight
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainThemeColor
import io.dev.relic.R

@Suppress("FunctionName")
fun LazyListScope.HomeTopBar(onOpenDrawer: () -> Unit) {
    item {
        HomeTopBarContent(onOpenDrawer)
    }
}

@Composable
private fun HomeTopBarContent(onOpenDrawer: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = mainThemeColor,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight()
                .systemBarsPadding()
        ) {
            IconButton(
                onClick = onOpenDrawer,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Apps,
                    contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                    tint = mainButtonColorLight
                )
            }
            Text(
                text = stringResource(R.string.home_title),
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = RelicFontFamily.ubuntu,
                    textMotion = TextMotion.Animated
                )
            )
        }
    }
}

@Composable
@Preview
private fun HomeTopBarContentPreview() {
    HomeTopBarContent(onOpenDrawer = {})
}