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
import io.core.ui.CommonNoDataComponent
import io.data.model.news.NewsArticleModel
import io.data.model.news.NewsArticleModel.Companion.testList

@Composable
fun NewsPanel(
    currentSelectedTab: Int,
    modelList: List<NewsArticleModel?>?,
    onTabItemClick: (currentSelectedTab: Int, selectedItem: String) -> Unit,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit
) {
    val lazyListState: LazyListState = rememberLazyListState()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        NewsTabBar(
            currentSelectedTab = currentSelectedTab,
            onTabItemClick = onTabItemClick,
            modifier = Modifier.statusBarsPadding()
        )
        if (modelList.isNullOrEmpty()) {
            CommonNoDataComponent()
        } else {
            NewsCardList(
                lazyListState = lazyListState,
                modelList = modelList,
                onCardClick = onCardClick,
                onLikeClick = onLikeClick,
                onShareClick = onShareClick
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282C34)
private fun NewsPanelPreview() {
    NewsPanel(
        currentSelectedTab = 0,
        modelList = testList(),
        onTabItemClick = { _: Int, _: String -> },
        onCardClick = {},
        onLikeClick = {},
        onShareClick = {}
    )
}