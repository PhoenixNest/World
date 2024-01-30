package io.dev.relic.feature.pages.hive.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import io.dev.relic.feature.function.news.ui.NewsTabBar

@Suppress("FunctionName")
@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.HiveTabBar(
    currentSelectedTab: Int,
    lazyListState: LazyListState,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
) {
    stickyHeader {
        NewsTabBar(
            currentSelectedTab = currentSelectedTab,
            lazyListState = lazyListState,
            onTabItemClick = onTabItemClick
        )
    }
}