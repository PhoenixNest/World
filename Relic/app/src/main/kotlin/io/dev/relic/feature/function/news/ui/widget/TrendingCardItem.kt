package io.dev.relic.feature.function.news.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.theme.RelicFontFamily.googleSans
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.widget.CommonAsyncImage
import io.data.model.news.NewsArticleModel

@Composable
fun TrendingCardItem(
    data: NewsArticleModel,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    data.apply {
        Surface(
            modifier = modifier.width(300.dp),
            border = BorderStroke(
                width = 2.dp,
                color = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(16.dp)
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
                    imageHeight = 240.dp,
                    imageRadius = 16.dp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = MaterialTheme.colorScheme.surfaceContainer.copy(alpha = 0.8F))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(
                        space = 6.dp,
                        alignment = Alignment.Top
                    ),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = title ?: "Title",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = newsReader,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "$source - $author",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = googleSans,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(
                        text = publishDate ?: "",
                        color = MaterialTheme.colorScheme.onSurface,
                        fontFamily = googleSans,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun TrendingCardItemPreview() {
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        TrendingCardItem(
            data = NewsArticleModel(
                title = "Just this... and WoW will be perfect for me.",
                subtitle = "(First of all, English is not my first language so forgive me if something is weird..) \\n \\nIt seems that the gypsy witch who stopped me one day while I was going to work and told me that you are all NPCs, this is just a simulation and the world conspires in my…",
                author = "Fantazma",
                thumbnailImageUrl = "null",
                publishDate = "2023-11-28 T00:40:11Z",
                contentUrl = "https://www.mmo-champion.com/threads/2644396-Just-this-and-WoW-will-be-perfect-for-me?p=54329863#post54329863",
                source = "Mmo-champion.com"
            ),
            onCardClick = {}
        )
    }
}