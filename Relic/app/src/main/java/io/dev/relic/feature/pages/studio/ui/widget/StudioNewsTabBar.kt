package io.dev.relic.feature.pages.studio.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.theme.mainThemeColor
import io.dev.relic.feature.function.news.ui.NewsTabBar

@OptIn(ExperimentalFoundationApi::class)
@Suppress("FunctionName")
fun LazyListScope.StudioTabBar(
    currentSelectedTab: Int,
    lazyListState: LazyListState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    stickyHeader {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = mainThemeColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            NewsTabBar(
                currentSelectedTab = currentSelectedTab,
                lazyListState = lazyListState,
                onTabItemClick = onTabItemClick
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}