package io.dev.relic.feature.function.news.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.data.model.news.NewsArticleModel

@Composable
fun NewsPanel() {
    val lazyListState: LazyListState = rememberLazyListState()
    val testModelList: List<NewsArticleModel> = NewsArticleModel.testList()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        NewsTabBar(
            currentSelectedTab = 0,
            onTabItemClick = { _: Int, _: String -> },
            modifier = Modifier.statusBarsPadding()
        )
        NewsCardList(
            lazyListState = lazyListState,
            modelList = testModelList,
            onCardClick = {},
            onLikeClick = {},
            onShareClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282C34)
private fun NewsPanelPreview() {
    NewsPanel()
}