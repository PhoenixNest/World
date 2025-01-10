package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.dev.relic.feature.function.news.ui.NewsTabBar

data class HomeNewsTabBarAction(
    val currentSelectedTab: Int,
    @Stable val lazyListState: LazyListState,
    @Stable val onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit
)

@Composable
fun HomeNewsTabBar(
    action: HomeNewsTabBarAction,
    modifier: Modifier = Modifier
) {
    NewsTabBar(
        currentSelectedTab = action.currentSelectedTab,
        lazyListState = action.lazyListState,
        onTabItemClick = action.onTabItemClick,
        modifier = modifier
    )
}

@Composable
@Preview(showBackground = true)
private fun HomeNewsTabBarPreview() {
    HomeNewsTabBar(
        action = HomeNewsTabBarAction(
            currentSelectedTab = 0,
            lazyListState = rememberLazyListState(),
            onTabItemClick = { _, _ -> }
        )
    )
}