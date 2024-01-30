package io.dev.relic.feature.pages.hive.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.core.ui.theme.RelicFontFamily
import io.core.ui.theme.mainTextColor
import io.dev.relic.R

@Suppress("FunctionName")
fun LazyListScope.HiveNewsTitle() {
    item {
        Text(
            text = stringResource(R.string.news_title),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            maxLines = 1,
            style = TextStyle(
                color = mainTextColor,
                fontSize = 72.sp,
                fontFamily = RelicFontFamily.newsReader,
            )
        )
    }
}