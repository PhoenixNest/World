package io.dev.relic.feature.pages.home.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.core.ui.theme.RelicFontFamily.newsReader
import io.dev.relic.R

@Composable
@Preview
fun HomeNewsTitle() {
    Text(
        text = stringResource(R.string.news_title),
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.onSurface,
        fontFamily = newsReader,
        textAlign = TextAlign.Center,
        maxLines = 1,
        style = MaterialTheme.typography.headlineMedium
    )
}