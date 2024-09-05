package io.dev.relic.feature.pages.studio.ui.bottom_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.Modifier
import io.dev.relic.feature.function.news.ui.NewsTabBar

@OptIn(ExperimentalFoundationApi::class)
@Suppress("FunctionName")
fun LazyListScope.StudioNewsTabBar(
    currentSelectedTab: Int,
    lazyListState: LazyListState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    modifier: Modifier = Modifier
) {
    stickyHeader {
        NewsTabBar(
            currentSelectedTab = currentSelectedTab,
            lazyListState = lazyListState,
            onTabItemClick = onTabItemClick,
            modifier = modifier
        )
    }
}