package io.dev.relic.feature.pages.studio.ui.bottom_sheet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.theme.mainBackgroundColorLight
import io.core.ui.theme.mainTextColor
import io.dev.relic.R

@OptIn(ExperimentalFoundationApi::class)
@Suppress("FunctionName")
fun LazyListScope.StudioNewsTitle(modifier: Modifier = Modifier) {
    stickyHeader {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Box(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .height(4.dp)
                        .width(96.dp)
                        .align(Alignment.Center)
                        .background(
                            color = mainBackgroundColorLight,
                            shape = RoundedCornerShape(16.dp)
                        )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.news_title),
                maxLines = 1,
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 32.sp,
                    fontFamily = newsReader,
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}