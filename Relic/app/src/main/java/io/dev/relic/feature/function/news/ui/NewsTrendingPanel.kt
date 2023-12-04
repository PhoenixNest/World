package io.dev.relic.feature.function.news.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.CommonAsyncImage
import io.core.ui.CommonNoDataComponent
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainBackgroundColor
import io.core.ui.theme.mainTextColor
import io.data.model.news.NewsArticleModel

@Composable
fun NewsTrendingPanel(
    modelList: List<NewsArticleModel?>?,
    onCardClick: (model: NewsArticleModel) -> Unit,
    lazyListState: LazyListState
) {
    if (modelList.isNullOrEmpty()) {
        CommonNoDataComponent()
    } else {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Start
            ),
            verticalAlignment = Alignment.Top
        ) {
            itemsIndexed(modelList) { index: Int, data: NewsArticleModel? ->
                if (data == null) {
                    //
                } else {
                    val itemDecorationModifier: Modifier = Modifier.padding(
                        start = if (index == 0) 16.dp else 0.dp,
                        end = if (index == modelList.size - 1) 16.dp else 0.dp
                    )
                    NewsTrendingCardItem(
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
private fun NewsTrendingCardItem(
    data: NewsArticleModel,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    data.apply {
        Surface(
            modifier = modifier
                .width(300.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            color = mainBackgroundColor.copy(alpha = 0.3F)
        ) {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onCardClick.invoke()
                    },
                contentAlignment = Alignment.BottomEnd
            ) {
                CommonAsyncImage(
                    url = thumbnailImageUrl,
                    imageWidth = 300.dp,
                    imageHeight = 240.dp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(color = mainBackgroundColor.copy(alpha = 0.8F))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 6.dp,
                        alignment = Alignment.Top
                    ),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = title ?: "Title",
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            color = mainTextColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = RelicFontFamily.newsReader
                        )
                    )
                    Text(
                        text = "$source - $author",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = TextStyle(
                            color = mainTextColor,
                            fontWeight = FontWeight.Bold,
                            fontFamily = RelicFontFamily.ubuntu
                        )
                    )
                    Text(
                        text = publishDate ?: "",
                        style = TextStyle(
                            color = mainTextColor,
                            fontFamily = RelicFontFamily.ubuntu
                        )
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun NewsTrendingPanelPreview() {
    NewsTrendingPanel(
        modelList = NewsArticleModel.testList(),
        onCardClick = {},
        lazyListState = rememberLazyListState()
    )
}