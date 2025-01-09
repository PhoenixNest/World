package io.dev.relic.feature.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.RelicUiUtil.getCurrentScreenHeightDp
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.pages.home.ui.widget.HomeNewsTitle
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeBottomSheetNewsTabBar
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeNewsTabBarAction
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeTopHeadlineNewsColumn
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeTopHeadlineNewsColumnAction
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeTrendingNewsRow
import io.dev.relic.feature.pages.home.ui.widget.bottom_sheet.HomeTrendingNewsRowAction

/**
 * If the content title is not tagged by "Remove", then we think the content itself is available.
 *
 * @param data      Original news article data.
 * */
internal fun isAvailableContent(data: NewsArticleModel): Boolean {
    return data.title.toString()
        .lowercase()
        .trim()
        .contains("remove")
        .not()
}

@Composable
fun HomePageBottomSheet(
    trendingNewsRowAction: HomeTrendingNewsRowAction,
    newsTabBarAction: HomeNewsTabBarAction,
    topHeadlineNewsColumnAction: HomeTopHeadlineNewsColumnAction
) {
    val statusBarHeight = 24.dp
    val currentScreenHeightDp = getCurrentScreenHeightDp()
    val sheetContentHeight = currentScreenHeightDp - statusBarHeight

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(sheetContentHeight),
        state = topHeadlineNewsColumnAction.lazyListState,
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        item { HomeNewsTitle() }
        item { HomeTrendingNewsRow(action = trendingNewsRowAction) }
        item { HomeBottomSheetNewsTabBar(action = newsTabBarAction) }
        HomeTopHeadlineNewsColumn(newsColumnAction = topHeadlineNewsColumnAction)
        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}