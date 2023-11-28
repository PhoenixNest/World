package io.dev.relic.feature.function.news.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColorDark
import io.data.model.news.NewsArticleModel
import java.time.LocalTime

@Composable
fun NewsCardList(
    lazyListState: LazyListState,
    modelList: List<NewsArticleModel?>,
    onLikeClick: (NewsArticleModel) -> Unit,
    onShareClick: (NewsArticleModel) -> Unit
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
                NewsCardItem(
                    data = data,
                    onLikeClick = { onLikeClick.invoke(data) },
                    onShareClick = { onShareClick.invoke(data) }
                )
            }
        }
    }
}

@Composable
private fun NewsCardItem(
    data: NewsArticleModel,
    onLikeClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    data.apply {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            NewsCardItemIntro(
                title = title ?: "Title",
                thumbnailImageUrl = thumbnailImageUrl ?: "",
                publishDate = publishDate ?: LocalTime.now().toString()
            )
            Spacer(modifier = Modifier.height(8.dp))
            NewsCardItemDesc(
                author = author ?: "Author",
                description = subtitle ?: "Subtitle",
                source = source ?: ""
            )
            Spacer(modifier = Modifier.height(8.dp))
            NewsCardFunctionBar(
                sourceUrl = contentUrl ?: "",
                onLikeClick = onLikeClick,
                onShareClick = onShareClick
            )
        }
    }
}

@Composable
private fun NewsCardItemIntro(
    title: String,
    thumbnailImageUrl: String,
    publishDate: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        CommonAsyncImage(
            url = thumbnailImageUrl,
            imageWidth = 56.dp,
            imageHeight = 56.dp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = publishDate,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(color = mainTextColor)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = TextStyle(color = mainTextColorDark)
            )
        }
    }
}

@Composable
private fun NewsCardItemDesc(
    author: String,
    description: String,
    source: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$source - $author",
            style = TextStyle(color = mainTextColor)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle()
        )
    }
}

@Composable
private fun NewsCardFunctionBar(
    sourceUrl: String,
    onLikeClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.End
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //
    }
}