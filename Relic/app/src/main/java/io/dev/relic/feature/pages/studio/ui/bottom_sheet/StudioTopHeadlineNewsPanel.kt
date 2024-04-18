package io.dev.relic.feature.pages.studio.ui.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.CommonLoadingPlaceholder
import io.core.ui.CommonReachTheEndComponent
import io.core.ui.CommonRetryComponent
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.ui.widget.NewsCardItem

@Suppress("FunctionName")
fun LazyListScope.StudioTopHeadlineNewsList(
    dataState: TopHeadlineNewsDataState,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    onRetryClick: () -> Unit,
    onScrollToTopClick: () -> Unit
) {
    when (dataState) {
        is TopHeadlineNewsDataState.Init,
        is TopHeadlineNewsDataState.Fetching -> {
            item {
                CommonLoadingPlaceholder()
            }
        }

        is TopHeadlineNewsDataState.Empty,
        is TopHeadlineNewsDataState.FetchFailed,
        is TopHeadlineNewsDataState.NoNewsData -> {
            item {
                CommonRetryComponent(
                    onRetryClick = onRetryClick,
                    modifier = Modifier.padding(12.dp),
                    containerHeight = 300.dp
                )
            }
        }

        is TopHeadlineNewsDataState.FetchSucceed -> {
            val dataList = dataState.modelList.filterNotNull()
            itemsIndexed(dataList) { index, data ->
                Column(
                    modifier = Modifier.wrapContentSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    if (isAvailableContent(data)) {
                        val isReachTheEnd = (index == dataState.modelList.size - 1)
                        val itemDecorationModifier = Modifier.padding(
                            top = if (index == 0) 0.dp else 16.dp,
                        )
                        NewsCardItem(
                            data = data,
                            onCardClick = { onCardClick.invoke(data) },
                            onLikeClick = { onLikeClick.invoke(data) },
                            onShareClick = { onShareClick.invoke(data) },
                            modifier = itemDecorationModifier
                        )
                        if (isReachTheEnd) {
                            CommonReachTheEndComponent(onScrollToTopClick = onScrollToTopClick)
                            Spacer(modifier = Modifier.padding(bottom = 72.dp))
                        }
                    }
                }
            }
        }
    }
}

/**
 * If the content title is not tagged by "Remove", then we think the content itself is available.
 *
 * @param data      Original news article data.
 * */
private fun isAvailableContent(data: NewsArticleModel): Boolean {
    return data.title.toString().lowercase().trim().contains("remove").not()
}