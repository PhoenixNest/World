package io.dev.relic.feature.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.dev.relic.feature.pages.home.ui.widget.HomeNewsTabBar
import io.dev.relic.feature.pages.home.ui.widget.HomeNewsTabBarAction
import io.dev.relic.feature.pages.home.ui.widget.HomeTopHeadlineNewsColumn
import io.dev.relic.feature.pages.home.ui.widget.HomeTopHeadlineNewsColumnAction
import io.dev.relic.feature.pages.home.ui.widget.HomeTrendingNewsRow
import io.dev.relic.feature.pages.home.ui.widget.HomeTrendingNewsRowAction

@Composable
fun HomeExpendSidePanel(
    trendingNewsRowAction: HomeTrendingNewsRowAction,
    newsTabBarAction: HomeNewsTabBarAction,
    topHeadlineNewsColumnAction: HomeTopHeadlineNewsColumnAction
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = topHeadlineNewsColumnAction.lazyListState,
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start,
        contentPadding = PaddingValues(top = 12.dp)
    ) {
        item { HomeTrendingNewsRow(action = trendingNewsRowAction) }
        item { HomeNewsTabBar(action = newsTabBarAction) }
        HomeTopHeadlineNewsColumn(newsColumnAction = topHeadlineNewsColumnAction)
        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}