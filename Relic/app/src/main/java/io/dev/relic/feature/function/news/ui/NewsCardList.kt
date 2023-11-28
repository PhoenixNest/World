package io.dev.relic.feature.function.news.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.data.model.news.NewsArticleModel

@Composable
fun NewsCardList(
    lazyListState: LazyListState,
    modelList: List<NewsArticleModel?>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp,
            Alignment.CenterVertically
        )
    ) {
        itemsIndexed(items = modelList) { index: Int, data: NewsArticleModel? ->
            if (data == null) {
                //
            } else {

            }
        }
    }
}