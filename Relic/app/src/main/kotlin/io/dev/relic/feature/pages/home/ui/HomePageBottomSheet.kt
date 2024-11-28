package io.dev.relic.feature.pages.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.utils.RelicUiUtil
import io.data.model.news.NewsArticleModel
import io.dev.relic.R
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
fun isAvailableContent(data: NewsArticleModel): Boolean {
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
    val topBarHeight = 72.dp
    val currentScreenHeightDp = RelicUiUtil.getCurrentScreenHeightDp()
    val sheetContentHeight = currentScreenHeightDp - statusBarHeight - topBarHeight

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(sheetContentHeight)
            .navigationBarsPadding(),
        state = topHeadlineNewsColumnAction.lazyListState,
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(
                text = stringResource(R.string.news_title),
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = newsReader,
                textAlign = TextAlign.Center,
                maxLines = 1,
                style = MaterialTheme.typography.headlineMedium
            )
        }
        item { HomeTrendingNewsRow(action = trendingNewsRowAction) }
        item { HomeBottomSheetNewsTabBar(action = newsTabBarAction) }
        HomeTopHeadlineNewsColumn(newsColumnAction = topHeadlineNewsColumnAction)
        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}