package io.dev.relic.feature.function.news.ui.widget

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.CommonAsyncImage
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainBackgroundColor
import io.core.ui.theme.mainTextColor
import io.data.model.news.NewsArticleModel

@Composable
fun TrendingCardItem(
    data: NewsArticleModel,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    data.apply {
        Surface(
            modifier = modifier
                .width(300.dp),
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
                    imageHeight = 240.dp,
                    imageRadius = 16.dp
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
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