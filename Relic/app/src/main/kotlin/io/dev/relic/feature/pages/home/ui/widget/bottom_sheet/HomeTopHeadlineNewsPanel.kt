package io.dev.relic.feature.pages.home.ui.widget.bottom_sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonLoadingPlaceholder
import io.core.ui.CommonReachTheEndComponent
import io.core.ui.CommonRetryComponent
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.function.news.TopHeadlineNewsDataState
import io.dev.relic.feature.function.news.ui.widget.NewsCardItem
import io.dev.relic.feature.pages.home.ui.isAvailableContent

data class HomeTopHeadlineNewsColumnAction(
    val state: TopHeadlineNewsDataState,
    @Stable val lazyListState: LazyListState,
    @Stable val onCardClick: (model: NewsArticleModel) -> Unit,
    @Stable val onLikeClick: (model: NewsArticleModel) -> Unit,
    @Stable val onShareClick: (model: NewsArticleModel) -> Unit,
    @Stable val onRetryClick: () -> Unit,
    @Stable val onScrollToTopClick: () -> Unit
)

@Suppress("FunctionName")
fun LazyListScope.HomeTopHeadlineNewsColumn(newsColumnAction: HomeTopHeadlineNewsColumnAction) {
    when (newsColumnAction.state) {
        is TopHeadlineNewsDataState.Init,
        is TopHeadlineNewsDataState.Fetching -> {
            item {
                CommonLoadingPlaceholder(
                    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            }
        }

        is TopHeadlineNewsDataState.Empty,
        is TopHeadlineNewsDataState.FetchFailed,
        is TopHeadlineNewsDataState.NoNewsData -> {
            item {
                CommonRetryComponent(
                    onRetryClick = newsColumnAction.onRetryClick,
                    modifier = Modifier.padding(12.dp),
                    containerHeight = 300.dp,
                    backgroundColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            }
        }

        is TopHeadlineNewsDataState.FetchSucceed -> {
            val modelList = newsColumnAction.state.modelList
            val dataList = modelList.filterNotNull()
            val sortList = dataList.sortedBy { it.publishDate }.reversed()
            itemsIndexed(sortList) { index, data ->
                Column {
                    if (isAvailableContent(data)) {
                        NewsCardItem(
                            data = data,
                            onCardClick = { newsColumnAction.onCardClick.invoke(data) },
                            onLikeClick = { newsColumnAction.onLikeClick.invoke(data) },
                            onShareClick = { newsColumnAction.onShareClick.invoke(data) }
                        )
                    }
                    if (index == sortList.size - 1) {
                        CommonReachTheEndComponent(onScrollToTopClick = newsColumnAction.onScrollToTopClick)
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HomeTopHeadlineNewsColumnPreview() {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            alignment = Alignment.Top
        )
    ) {
        HomeTopHeadlineNewsColumn(
            newsColumnAction = HomeTopHeadlineNewsColumnAction(
                state = TopHeadlineNewsDataState.FetchSucceed(
                    modelList = listOf(
                        NewsArticleModel(
                            title = "Just this... and WoW will be perfect for me.",
                            subtitle = "(First of all, English is not my first language so forgive me if something is weird..) \\n \\nIt seems that the gypsy witch who stopped me one day while I was going to work and told me that you are all NPCs, this is just a simulation and the world conspires in my…",
                            author = "Fantazma",
                            thumbnailImageUrl = "null",
                            publishDate = "2023-11-28 T00:40:11Z",
                            contentUrl = "https://www.mmo-champion.com/threads/2644396-Just-this-and-WoW-will-be-perfect-for-me?p=54329863#post54329863",
                            source = "Mmo-champion.com"
                        ),
                        NewsArticleModel(
                            title = "House speaker suggest George Santos will quit rather than be expelled",
                            subtitle = "Speaker of the House Mike Johnson on Monday afternoon said he has spoken to U.S. Rep. George Santos, suggesting the embattled and indicted New York Republican might resign rather than face an impending expulsion vote he’s likely to lose.But over the holiday w…",
                            author = "David Badash, The New Civil Rights Movement",
                            thumbnailImageUrl = "https://www.rawstory.com/media-library/george-santos.jpg?id=32972710&width=1200&height=600&coordinates=0%2C25%2C0%2C25",
                            publishDate = "2023-11-27 T21:08:30Z",
                            contentUrl = "https://www.rawstory.com/house-speaker-suggest-george-santos-will-quit-rather-than-be-expelled/",
                            source = "Raw Story"
                        )
                    )
                ),
                lazyListState = lazyListState,
                onCardClick = {},
                onLikeClick = {},
                onShareClick = {},
                onRetryClick = {},
                onScrollToTopClick = {}
            )
        )
    }
}