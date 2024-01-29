package io.dev.relic.feature.function.news.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants
import io.common.util.TimeUtil
import io.core.ui.CommonAsyncImage
import io.core.ui.CommonNoDataComponent
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainBackgroundColor
import io.core.ui.theme.mainButtonColorLightDark
import io.core.ui.theme.mainTextColor
import io.data.model.news.NewsArticleModel

@Composable
fun NewsListPanel(
    modelList: List<NewsArticleModel?>?,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    lazyListState: LazyListState
) {
    if (modelList.isNullOrEmpty()) {
        CommonNoDataComponent()
    } else {
        NewsCardList(
            modelList = modelList,
            onCardClick = onCardClick,
            onLikeClick = onLikeClick,
            onShareClick = onShareClick,
            lazyListState = lazyListState
        )
    }
}

@Composable
private fun NewsCardList(
    modelList: List<NewsArticleModel?>,
    onCardClick: (model: NewsArticleModel) -> Unit,
    onLikeClick: (model: NewsArticleModel) -> Unit,
    onShareClick: (model: NewsArticleModel) -> Unit,
    lazyListState: LazyListState
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        state = lazyListState,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(
            space = 16.dp,
            alignment = Alignment.Top
        ),
        contentPadding = PaddingValues(horizontal = 8.dp)
    ) {
        itemsIndexed(items = modelList) { index: Int, data: NewsArticleModel? ->
            if (data == null) {
                //
            } else {
                val itemDecorationModifier = Modifier.padding(
                    top = if (index == 0) 16.dp else 0.dp,
                    bottom = if (index == modelList.size - 1) 120.dp else 0.dp
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

@Composable
fun NewsCardItem(
    data: NewsArticleModel,
    onCardClick: () -> Unit,
    onLikeClick: () -> Unit,
    onShareClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    data.apply {
        Surface(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            color = mainBackgroundColor.copy(alpha = 0.3F)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(color = mainBackgroundColor.copy(alpha = 0.3F))
                    .clickable { onCardClick.invoke() }
                    .padding(20.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                NewsCardItemIntro(
                    title = title ?: "Title",
                    thumbnailImageUrl = thumbnailImageUrl,
                    publishDate = publishDate ?: TimeUtil.getCurrentTime().toString()
                )
                Spacer(modifier = Modifier.height(18.dp))
                NewsCardItemDesc(
                    author = author ?: "Author",
                    description = subtitle ?: "Subtitle",
                    source = source ?: ""
                )
                Spacer(modifier = Modifier.height(8.dp))
                NewsCardFunctionBar(
                    onLikeClick = onLikeClick,
                    onShareClick = onShareClick
                )
            }
        }
    }
}

@Composable
private fun NewsCardItemIntro(
    title: String,
    thumbnailImageUrl: String?,
    publishDate: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(96.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        if (!thumbnailImageUrl.isNullOrEmpty()) {
            CommonAsyncImage(
                url = thumbnailImageUrl,
                imageWidth = 96.dp,
                imageHeight = 96.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = publishDate,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = mainTextColor,
                    fontFamily = ubuntu
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = newsReader,
                    lineHeight = TextUnit(
                        value = 1.6F,
                        type = TextUnitType.Em
                    )
                )
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
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = TextStyle(
                color = mainTextColor,
                fontWeight = FontWeight.Bold,
                fontFamily = ubuntu
            )
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = description,
            overflow = TextOverflow.Ellipsis,
            maxLines = 5,
            style = TextStyle(
                color = mainTextColor.copy(alpha = 0.5F),
                fontFamily = ubuntu,
                lineHeight = TextUnit(
                    value = 1.6F,
                    type = TextUnitType.Em
                )
            )
        )
    }
}

@Composable
private fun NewsCardFunctionBar(
    onLikeClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onLikeClick) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = mainButtonColorLightDark
            )
        }
        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = mainButtonColorLightDark
            )
        }
        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = RelicConstants.ComposeUi.DEFAULT_DESC,
                tint = mainButtonColorLightDark
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF282C34)
private fun NewsListPanelPreview() {
    NewsListPanel(
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
        onLikeClick = {},
        onShareClick = {},
        lazyListState = rememberLazyListState()
    )
}