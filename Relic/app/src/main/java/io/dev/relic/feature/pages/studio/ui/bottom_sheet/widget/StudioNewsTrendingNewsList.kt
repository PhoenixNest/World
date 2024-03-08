package io.dev.relic.feature.pages.studio.ui.bottom_sheet.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.core.ui.CommonLoadingPlaceholder
import io.core.ui.CommonNoDataComponent
import io.core.ui.CommonRetryComponent
import io.data.model.news.NewsArticleModel
import io.dev.relic.feature.function.news.TrendingNewsDataState
import io.dev.relic.feature.function.news.ui.widget.TrendingCardItem

@Suppress("FunctionName")
fun LazyListScope.StudioTrendingNewsList(
    dataState: TrendingNewsDataState,
    lazyListState: LazyListState,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onRetryClick: () -> Unit
) {
    item {
        when (dataState) {
            is TrendingNewsDataState.Init,
            is TrendingNewsDataState.Fetching -> {
                CommonLoadingPlaceholder(false)
            }

            is TrendingNewsDataState.Empty,
            is TrendingNewsDataState.FetchFailed,
            is TrendingNewsDataState.NoNewsData -> {
                CommonRetryComponent(
                    onRetryClick = onRetryClick,
                    modifier = Modifier.padding(12.dp),
                    containerHeight = 196.dp
                )
            }

            is TrendingNewsDataState.FetchSucceed -> {
                StudioTrendingNewsPanel(
                    modelList = dataState.modelList,
                    onCardClick = onCardClick,
                    lazyListState = lazyListState
                )
            }
        }
    }
}

@Composable
private fun StudioTrendingNewsPanel(
    modelList: List<NewsArticleModel?>?,
    onCardClick: (model: NewsArticleModel) -> Unit,
    lazyListState: LazyListState
) {
    if (modelList.isNullOrEmpty()) {
        CommonNoDataComponent()
    } else {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.Top
        ) {
            itemsIndexed(modelList) { index, data ->
                if (data == null) {
                    //
                } else {
                    val itemDecorationModifier = Modifier.padding(
                        start = if (index == 0) 16.dp else 0.dp,
                        end = if (index == modelList.size - 1) 16.dp else 0.dp
                    )
                    TrendingCardItem(
                        data = data,
                        onCardClick = { onCardClick.invoke(data) },
                        modifier = itemDecorationModifier
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun StudioTrendingPanelPreview() {
    StudioTrendingNewsPanel(
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
        ),
        onCardClick = {},
        lazyListState = rememberLazyListState()
    )
}