package io.dev.relic.feature.function.news.ui.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.common.util.TimeUtil
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily.googleSans
import io.core.ui.theme.RelicFontFamily.newsReader
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
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCardClick.invoke() }
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(
                    space = 12.dp,
                    alignment = Alignment.Top
                ),
                horizontalAlignment = Alignment.Start
            ) {
                NewsCardItemIntro(
                    title = title ?: "Title",
                    thumbnailImageUrl = thumbnailImageUrl,
                    publishDate = publishDate ?: TimeUtil.getCurrentTime().toString()
                )
                NewsCardItemDesc(
                    author = author ?: "Author",
                    description = subtitle ?: "Subtitle",
                    source = source ?: ""
                )
                // NewsCardFunctionBar(
                //     onLikeClick = onLikeClick,
                //     onShareClick = onShareClick
                // )
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
                imageHeight = 96.dp,
                imageRadius = 12.dp
            )
            Spacer(modifier = Modifier.width(12.dp))
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(
                space = 8.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = publishDate,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontFamily = googleSans,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onTertiaryContainer,
                fontFamily = newsReader,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                overflow = TextOverflow.Ellipsis,
                lineHeight = TextUnit(
                    value = 1.4F,
                    type = TextUnitType.Em
                ),
                style = MaterialTheme.typography.titleMedium
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
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            fontFamily = googleSans,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.5F),
            fontFamily = googleSans,
            overflow = TextOverflow.Ellipsis,
            maxLines = 5,
            style = MaterialTheme.typography.bodyMedium
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
                contentDescription = DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        IconButton(onClick = onShareClick) {
            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = DEFAULT_DESC,
                tint = MaterialTheme.colorScheme.onSecondaryContainer
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