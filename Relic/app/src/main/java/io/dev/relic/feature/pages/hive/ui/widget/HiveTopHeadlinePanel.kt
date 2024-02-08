package io.dev.relic.feature.pages.hive.ui.widget

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.core.ui.CommonLoadingPlaceholder
import io.core.ui.CommonRetryComponent
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.ui.widget.NewsCardItem

@Suppress("FunctionName")
fun LazyListScope.HiveTopHeadlineNewsList(
    dataState: TopHeadlineNewsDataState,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    onRetryClick: () -> Unit
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
            itemsIndexed(dataState.modelList) { index, data ->
                if (data == null) {
                    //
                } else {
                    val itemDecorationModifier = Modifier.padding(
                        top = if (index == 0) 0.dp else 16.dp,
                        bottom = if (index == dataState.modelList.size - 1) 120.dp else 0.dp
                    )
                    NewsCardItem(
                        data = data,
                        onCardClick = { onCardClick.invoke(data) },
                        onLikeClick = { onLikeClick.invoke(data) },
                        onShareClick = { onShareClick.invoke(data) },
                        modifier = itemDecorationModifier
                    )
                }
            }
        }
    }
}