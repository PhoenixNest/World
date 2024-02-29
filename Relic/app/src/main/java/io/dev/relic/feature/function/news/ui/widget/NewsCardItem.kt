package io.dev.relic.feature.function.news.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.theme.RelicFontFamily.ubuntu
import io.core.ui.theme.mainBackgroundColor
import io.core.ui.theme.mainButtonColorLightDark
import io.core.ui.theme.mainTextColor
import io.core.ui.theme.mainTextColor50
import io.data.model.news.NewsArticleModel

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
                color = mainTextColor50,
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
@Preview
private fun NewsListItemPreview() {
    NewsCardItem(
        data = NewsArticleModel(
            title = "Just this... and WoW will be perfect for me.",
            subtitle = "(First of all, English is not my first language so forgive me if something is weird..) \\n \\nIt seems that the gypsy witch who stopped me one day while I was going to work and told me that you are all NPCs, this is just a simulation and the world conspires in my…",
            author = "Fantazma",
            thumbnailImageUrl = "null",
            publishDate = "2023-11-28 T00:40:11Z",
            contentUrl = "https://www.mmo-champion.com/threads/2644396-Just-this-and-WoW-will-be-perfect-for-me?p=54329863#post54329863",
            source = "Mmo-champion.com"
        ),
        onCardClick = {},
        onLikeClick = {},
        onShareClick = {}
    )
}