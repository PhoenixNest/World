package io.dev.relic.feature.pages.hive.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.common.RelicConstants.ComposeUi.DEFAULT_DESC
import io.core.ui.theme.RelicFontFamily.newsReader
import io.core.ui.theme.mainIconColorLight
import io.core.ui.theme.mainTextColor
import io.dev.relic.R

@Suppress("FunctionName")
fun LazyListScope.HiveNewsTitle(onResortClick: () -> Unit) {
    item {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(R.string.news_title),
                modifier = Modifier.align(Alignment.CenterStart),
                maxLines = 1,
                style = TextStyle(
                    color = mainTextColor,
                    fontSize = 32.sp,
                    fontFamily = newsReader,
                )
            )
            IconButton(
                onClick = onResortClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_sort),
                    contentDescription = DEFAULT_DESC,
                    tint = mainIconColorLight
                )
            }
        }
    }
}